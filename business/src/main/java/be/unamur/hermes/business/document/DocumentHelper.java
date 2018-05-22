package be.unamur.hermes.business.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.constants.EventConstants;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Event;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.entity.RequestParameters;
import be.unamur.hermes.dataaccess.entity.User;

/**
 * Utility methods to be called inside ThymeLeaf. No static methods because
 * Thymeleaf has very cumbersome syntax for them.
 * 
 * @author Thomas_Elskens
 *
 */
public class DocumentHelper {

    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected final RequestParameters params;
    protected final Request request;

    public DocumentHelper(RequestParameters params, Request request) {
	this.params = params;
	this.request = request;
    }

    public String getParameter(String parameterId) {
	return params.getParameter(parameterId);
    }

    public String getRequestField(String code) {
	Optional<RequestField> fieldOpt = request.getData().stream().filter(rf -> code.equalsIgnoreCase(rf.getCode()))
		.findFirst();
	if (!fieldOpt.isPresent())
	    throw new BusinessException(Errors.FAILURE_DOCUMENT_GENERATION,
		    "Request field with code '" + code + "' cannot be replaced");
	return fieldOpt.get().getFieldValue();
    }

    public String getAddressLine1(Address address) {
	StringBuilder sb = new StringBuilder();
	sb.append(address.getStreetNb());
	if (StringUtils.hasText(address.getNbSuffix()))
	    sb.append(address.getNbSuffix());
	sb.append(", ").append(address.getStreet());
	return sb.toString();
    }

    public String getAddressLine2(Address address) {
	return address.getZipCode() + " " + address.getMunicipality();
    }

    public String formatName(User user) {
	return user.getLastName() + " " + user.getFirstName();
    }

    public String getCreationDate(List<Event> events) {
	Event creationEvent = events.stream()
		.filter(ev -> EventConstants.TYPE_CREATED.equalsIgnoreCase(ev.getType().getDescription())).findFirst()
		.get();
	return format(creationEvent.getAt().toLocalDate());
    }

    /**
     * Thymeleaf syntax for formatting dates is cumbersome.
     * 
     * @param date
     * @return
     */
    public String format(LocalDate date) {
	return date.format(dtf);
    }

    protected boolean isCorporateRequest() {
	return request.getCompany() != null;
    }

    protected LocalDate getDocumentDate() {
	return LocalDate.now();
    }

}
