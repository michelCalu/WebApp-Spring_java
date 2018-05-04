package be.unamur.hermes.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.entity.RequestStatus;
import be.unamur.hermes.dataaccess.entity.RequestType;
import be.unamur.hermes.dataaccess.repository.DepartmentRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.RequestFieldRepository;
import be.unamur.hermes.dataaccess.repository.RequestRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestFieldRepository requestFieldRepository;
    private final MunicipalityRepository municipalityRepository;
    private final DepartmentRepository departmentRepository;
    private final DocumentService documentService;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestFieldRepository requestFieldRepository,
	    MunicipalityRepository municipalityRepository, DepartmentRepository departmentRepository,
	    DocumentService documentService) {
	super();
	this.requestRepository = requestRepository;
	this.requestFieldRepository = requestFieldRepository;
	this.municipalityRepository = municipalityRepository;
	this.departmentRepository = departmentRepository;
	this.documentService = documentService;
    }

    @Override
    public Request find(long requestId) {
	return requestRepository.findById(requestId);
    }

    @Override
    public long create(Request newRequest, Map<String, MultipartFile> codeToFiles) {
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
        try {
            for(String code : codeToFiles.keySet()){
                RequestField requestField = new RequestField();
                requestField.setCode(code);
                requestField.setFieldType("File");
                requestField.setFieldFile(codeToFiles.get(code).getBytes());
                newRequest.addRequestField(requestField);
                requestFieldRepository.createRequestField(requestField, newRequestId);
            }
        } catch (IOException e) {
            throw new BusinessException("Error when receiving files.");
        }

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

    @Override
    @Transactional
    public void update(Request updatedRequest) throws BusinessException {
	Request baseRequest = requestRepository.findById(updatedRequest.getId());
	// is the request approved/rejected ?
	if (updatedRequest.getStatus() != null) {
	    if (!checkTransition(baseRequest.getStatus(), updatedRequest.getStatus()))
		throw new BusinessException("Unauthorized workflow");
	    RequestStatus newStatus = updatedRequest.getStatus();
	    // some statusses need special traitment (document generation)
	    if (RequestService.STATUS_ACCEPTED.equalsIgnoreCase(newStatus.getName())) {
		approve(baseRequest);
	    } else if (RequestService.STATUS_REJECTED.equalsIgnoreCase(newStatus.getName())) {
		reject(baseRequest);
	    }
	    // for all statusses : update status
	    requestRepository.updateStatus(updatedRequest);
	}
    }

    /**
     * @return true if the status transition is valid, false otherwise
     */
    private boolean checkTransition(RequestStatus oldStatus, RequestStatus newStatus) {
	// TODO to be checked
	return true;
    }

    private void approve(Request request) {
	RequestType type = request.getType();
	// TODO all this could be done much better
	if (RequestService.TYPE_NATIONALITY_CERTIFICATE.equalsIgnoreCase(type.getDescription())) {
	    documentService.createNationalityCertificate(true, request);
	} else if (RequestService.TYPE_PARKING_CARD.equalsIgnoreCase(type.getDescription())) {
	    documentService.createParkingCard(request);
	    documentService.createParkingCardDecision(true, request);
	    documentService.createPayment(request);
	}
    }

    private void reject(Request request) {
	RequestType type = request.getType();
	// TODO all this could be done much better
	if (RequestService.TYPE_NATIONALITY_CERTIFICATE.equalsIgnoreCase(type.getDescription())) {
	    documentService.createNationalityCertificate(false, request);
	} else if (RequestService.TYPE_PARKING_CARD.equalsIgnoreCase(type.getDescription())) {
	    documentService.createParkingCardDecision(false, request);
	}
    }

    // TODO : implements skills and adapt this method
    private Department findRequestDepartment(Request request) {
	Citizen citizen = request.getCitizen();
	Municipality citizenMunicipality = municipalityRepository.findByName(citizen.getAddress().getMunicipality());
	List<Department> municipalityDepartments = departmentRepository
		.findByMunicipalityId(citizenMunicipality.getId());
	if (municipalityDepartments.isEmpty())
	    throw new BusinessException("The request type isn't managed by any service of the municipality.");
	else
	    return municipalityDepartments.get(0);
    }

    private String generateSystemRef(Request request) {
	String citizenNRN = request.getCitizen().getNationalRegisterNb();
	String nbOfCreatedRequest = Integer
		.toString(requestRepository.findByCitizen(request.getCitizen().getId()).size());
	return "REQ_" + citizenNRN + "_" + nbOfCreatedRequest;
    }
}
