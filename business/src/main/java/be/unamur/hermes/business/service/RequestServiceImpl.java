package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.*;
import be.unamur.hermes.dataaccess.repository.DepartmentRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.RequestFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestFieldRepository requestFieldRepository;
    private final MunicipalityRepository municipalityRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public RequestServiceImpl(
            RequestRepository requestRepository,
            RequestFieldRepository requestFieldRepository,
            MunicipalityRepository municipalityRepository,
            DepartmentRepository departmentRepository) {
	super();
	this.requestRepository = requestRepository;
	this.requestFieldRepository = requestFieldRepository;
	this.municipalityRepository = municipalityRepository;
	this.departmentRepository = departmentRepository;
    }

    @Override
    public Request find(long requestId) {
	return requestRepository.findById(requestId);
    }

    @Override
    public long create(Request newRequest) {
	    // TODO validate with Authentification
        // Setting basic info
	    RequestType requestType = findRequestTypeByDescription(newRequest.getTypeDescription());
	    newRequest.setType(requestType);
	    if (requestType == null)
	        throw new BusinessException("Unknown request type:" + requestType.getDescription());

        Department department = findRequestDepartment(newRequest);
        newRequest.setDepartment(department);
        newRequest.setSystemRef(generateSystemRef(newRequest));
        newRequest.setUserRef(Long.toString(newRequest.getCitizen().getId()));
        newRequest.setMunicipalityRef(Long.toString(department.getMunicipality().getId()));

        Long newRequestId = requestRepository.create(newRequest);

	    for(RequestField field : newRequest.getData()){
	        if(field.getFieldType().equals("String") && field.getFieldFile() != null)
	            throw new BusinessException("Field type doesn't match field content ! Expected 'String' got 'File'");
	        if(field.getFieldType().equals("File") && field.getFieldValue() != null)
	            throw new BusinessException("Field type doesn't match field content ! Expected 'File' got 'String'");
	        if(field.getFieldValue() == null && field.getFieldFile() == null)
	            throw new BusinessException("No value or file is attached to this field !");
	        requestFieldRepository.createRequestField(field, newRequestId);
	    }
	    return newRequestId;
    }

    @Override
    public List<Request> findByCitizenId(long citizenId) {
	return requestRepository.findByCitizen(citizenId);
    }

    @Override
    public List<Request> find(long citizenId, long requestTypeId) {
	return requestRepository.findByCitizen(citizenId, requestTypeId);
    }

    @Override
    public RequestType findRequestTypeByDescription(String description) {
	return requestRepository.findRequestTypeByDescription(description);
    }

    @Override
    public List<Request> findByDepartmentId(long departmentId) {
	return requestRepository.findbyDepartmentId(departmentId);
    }

    @Override
    public List<Request> findByAssigneeId(long assigneeId) {
	return requestRepository.findbyAssigneeId(assigneeId);
    }

    @Override
    public RequestType findRequestTypeById(long id) {
	return requestRepository.findRequestTypeById(id);
    }

    // TODO : implements skills and adapt this method
    private Department findRequestDepartment(Request request){
        Citizen citizen = request.getCitizen();
        Municipality citizenMunicipality = municipalityRepository.findByName(citizen.getAddress().getMunicipality());
        List<Department> municipalityDepartments = departmentRepository.findByMunicipalityId(citizenMunicipality.getId());
        if(municipalityDepartments.isEmpty())
            throw new BusinessException("The request type isn't managed by any service of the municipality.");
        else
            return municipalityDepartments.get(0);
    }

    private String generateSystemRef(Request request){
        String citizenNRN = request.getCitizen().getNationalRegisterNb();
        String nbOfCreatedRequest =
                Integer.toString(requestRepository.findByCitizen(request.getCitizen().getId()).size());
        return "REQ_" + citizenNRN + "_" + nbOfCreatedRequest;
    }
}
