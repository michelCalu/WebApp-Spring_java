package be.unamur.hermes.web.security;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.business.service.MunicipalityService;
import be.unamur.hermes.common.enums.Authority;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.UserAccount;

/**
 * This PermissionEvaluator evaluates if a user can be granted access to
 * requests and citizens.
 * <ul>
 * <li>In case of an admin : all request are granted ;</li>
 * <li>In case of a officer : Access will only be granted if the requested
 * objects pertain to the same municipality ;</li>
 * <li>In case of a simple citizen : Access will only be granted if the
 * requested objects pertain to the requesting citizen himself.</li>
 * </ul>
 * 
 * @author Thomas_Elskens
 *
 */
@Component
public class HermesPermissionEvaluator implements PermissionEvaluator {

    @Lazy
    @Autowired
    private MunicipalityService municipalityService;

    @Lazy
    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
	// TODO do something useful with the permission parameter
	UserAccount account = (UserAccount) authentication.getPrincipal();
	Set<Authority> authorities = EnumSet.copyOf(UserAccount.getRoles(account));
	if (authorities.contains(Authority.ADMIN))
	    return true; // to simplify things...
	Object requestBody;
	try {
	    requestBody = extractRequestBody(targetDomainObject);
	} catch (NoContentsException e) {
	    return true;
	}
	if (authorities.contains(Authority.OFFICER)) {
	    // check the officer references the same municipality as the target object
	    Municipality targetMunicipality = extractMunicipality(requestBody);
	    if (targetMunicipality == null)
		return false;
	    Employee employee = employeeService.findById(account.getTechnicalId());
	    if (employee == null || employee.getDepartmentIds().isEmpty())
		return false;
	    Municipality employeeMunicipality = municipalityService.findById(employee.getDepartmentIds().get(0));
	    return employeeMunicipality == null ? false : employeeMunicipality.getId() == targetMunicipality.getId();
	}
	if (authorities.contains(Authority.USER)) {
	    Citizen targetCitizen = extractCitizen(requestBody);
	    return targetCitizen == null ? false : targetCitizen.getId() == account.getTechnicalId();
	}
	return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
	    Object permission) {
	// TODO should be implemented
	return false;
    }

    private Municipality extractMunicipality(Object requestBody) {
	if (requestBody instanceof Request) {
	    Request request = (Request) requestBody;
	    return request.getDepartment().getMunicipality();
	}
	if (requestBody instanceof Citizen) {
	    Citizen ctz = (Citizen) requestBody;
	    return municipalityService.findByAddress(ctz.getAddress().getId());
	}
	return null;
    }

    private Citizen extractCitizen(Object requestBody) {
	if (requestBody instanceof Request) {
	    Request request = (Request) requestBody;
	    return request.getCitizen();
	}
	if (requestBody instanceof Citizen)
	    return (Citizen) requestBody;
	return null;
    }

    private Object extractRequestBody(Object targetDomainObject) {
	if (targetDomainObject instanceof ResponseEntity) {
	    ResponseEntity<?> ent = (ResponseEntity<?>) targetDomainObject;
	    // recurse on the body of the response entity
	    return extractRequestBody(ent.getBody());
	}

	if (targetDomainObject instanceof List) {
	    List<?> list = (List<?>) targetDomainObject;
	    if (list.isEmpty())
		throw new NoContentsException();
	    // recurse on the first element of the list
	    return extractRequestBody(list.get(0));
	}
	return targetDomainObject;
    }

    /**
     * Dummy exception to distinguish between the fact no valid Municipality could
     * be found (in which case permission should be denied) from the trivial case in
     * which the request did not return any data at all (in which case permission
     * cannot be easily determined in a centralized manner).
     * 
     * @author Thomas_Elskens
     *
     */
    private static class NoContentsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    }

}
