package be.unamur.hermes.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.constants.EventConstants;
import be.unamur.hermes.common.constants.RequestTypes;
import be.unamur.hermes.common.enums.RequestStatusInfo;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.entity.*;
import be.unamur.hermes.dataaccess.repository.*;

@Service
public class RequestServiceImpl implements RequestService, Errors {

    private final RequestRepository requestRepository;
    private final RequestTypeRepository requestTypeRepository;
    private final RequestFieldRepository requestFieldRepository;
    private final MunicipalityRepository municipalityRepository;
    private final DepartmentRepository departmentRepository;
    private final DocumentService documentService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestFieldRepository requestFieldRepository,
	    MunicipalityRepository municipalityRepository, DepartmentRepository departmentRepository,
	    DocumentService documentService, RequestTypeRepository requestTypeRepository) {
	super();
	this.requestRepository = requestRepository;
	this.requestFieldRepository = requestFieldRepository;
	this.municipalityRepository = municipalityRepository;
	this.departmentRepository = departmentRepository;
	this.documentService = documentService;
	this.requestTypeRepository = requestTypeRepository;
    }

    @Override
    public Request find(long requestId) {
	return requestRepository.findById(requestId);
    }

    // rollback for all exceptions, not only runtime exceptions and errors
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long create(Request newRequest, Map<String, MultipartFile> codeToFiles) {
	// TODO validate with Authentification
	// Setting basic info
	Department department = findRequestDepartment(newRequest);
	newRequest.setDepartment(department);
	newRequest.setSystemRef(generateSystemRef(newRequest));
	newRequest.setMunicipalityRef(generateMunicipalityRef(newRequest));

	Long newRequestId = requestRepository.create(newRequest);

	for (RequestField field : newRequest.getData()) {
	    if (field.getFieldType().equals("String") && field.getFieldFile() != null)
		throw new BusinessException(INVALID_REQUEST_FIELD,
			"Field type doesn't match field content ! Expected 'String' got 'File'");
	    if (field.getFieldType().equals("File") && field.getFieldValue() != null)
		throw new BusinessException(INVALID_REQUEST_FIELD,
			"Field type doesn't match field content ! Expected 'File' got 'String'");
	    if (field.getFieldValue() == null && field.getFieldFile() == null)
		throw new BusinessException(MISSING_REQUEST_FIELD, "No value or file is attached to this field !");
	    requestFieldRepository.createRequestField(field, newRequestId);
	}

	try {
	    for (String code : codeToFiles.keySet()) {
		MultipartFile file = codeToFiles.get(code);
		RequestField requestField = new RequestField();
		requestField.setCode(code);
		requestField.setFieldType("binary");
		requestField.setFieldValue(file.getOriginalFilename());
		requestField.setFieldFile(file.getBytes());
		requestField.setFieldFileType(file.getContentType());
		newRequest.addRequestField(requestField);
		requestFieldRepository.createRequestField(requestField, newRequestId);
	    }
	} catch (IOException e) {
	    throw new BusinessException(INVALID_FILE, "Error when receiving files.");
	}

	// register creation event
	UserAccount account = citizenService.findAccount(newRequest.getCitizen().getId());
	Event creationEvent = Event.create(EventConstants.TYPE_CREATED, account.getAccountUserId(), newRequestId);
	eventService.create(creationEvent);

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
    public List<Request> findByDepartmentId(long departmentId) {
	return requestRepository.findbyDepartmentId(departmentId);
    }

    @Override
    public List<Request> findByAssigneeId(long assigneeId) {
	return requestRepository.findbyAssigneeId(assigneeId);
    }

    @Override
    public List<Request> findByCompanyNb(String companyNb) {
	return requestRepository.findByCompanyNb(companyNb);
    }

    @Override
    public List<Request> findByCompanyNb(String companyNb, long requestTypeId) {
	return requestRepository.findByCompanyNb(companyNb, requestTypeId);
    }

    @Override
    public RequestField findRequestFieldByCode(long requestId, String code) {
	Request request = find(requestId);
	RequestField fieldOfCode = null;
	for (RequestField field : request.getData()) {
	    if (field.getCode().equals(code)) {
		if (fieldOfCode == null) {
		    if (field.getFieldFile() != null)
			fieldOfCode = field;
		    else
			throw new BusinessException(INVALID_REQUEST_FIELD,
				"Requested file identified by code : " + code + " isn't a file!");
		} else
		    throw new BusinessException(INVALID_REQUEST_FIELD,
			    "Multiple request fields of code : " + code + " detected.");
	    }
	}
	if (fieldOfCode != null)
	    return fieldOfCode;
	else
	    throw new BusinessException(MISSING_REQUEST_FIELD,
		    "No requestField of code : " + code + " has been found !");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Request update(Request updatedRequest) throws BusinessException {
	Request baseRequest = requestRepository.findById(updatedRequest.getId());
	// is the request approved/rejected ?
	if (updatedRequest.getStatus() != null) {
	    if (!checkTransition(baseRequest.getStatus(), updatedRequest.getStatus()))
		throw new BusinessException(INVALID_REQUEST_STATUS, "Unauthorized workflow");
	    RequestStatus newStatus = updatedRequest.getStatus();
	    // some statusses need special traitment (document generation)

	    if (RequestService.STATUS_ACCEPTED.equalsIgnoreCase(newStatus.getName())) {
		approve(baseRequest);
	    } else if (RequestService.STATUS_REJECTED.equalsIgnoreCase(newStatus.getName())) {
		reject(baseRequest);
	    }
	    // for all statusses : update status ...
	    requestRepository.updateStatus(updatedRequest);
	    // ... and create event
	    createStatusEvent(updatedRequest, baseRequest);
	    return requestRepository.findById(updatedRequest.getId());
	}

	if (updatedRequest.getAssignee() != null) {
	    requestRepository.updateAssignee(updatedRequest);
	    long userAccountId = employeeService.findAccount(updatedRequest.getAssignee().getId()).getAccountUserId();
	    Event statusEvent = Event.create(EventConstants.TYPE_ASSIGNEE_CHANGE, userAccountId,
		    updatedRequest.getId());
	    eventService.create(statusEvent);
	    requestRepository.findById(updatedRequest.getId());
	}
	return requestRepository.findById(updatedRequest.getId());
    }

    /**
     * @return true if the status transition is valid, false otherwise
     */
    private boolean checkTransition(RequestStatus oldStatus, RequestStatus newStatus) {
	// TODO to be checked
	return true;
    }

    private void approve(Request request) {
	String type = request.getTypeDescription();
	// TODO all this could be done much better
	if (RequestTypes.NATIONALITY_CERTIFICATE.equalsIgnoreCase(type)) {
	    documentService.createNationalityCertificate(true, request);
	} else if (RequestTypes.CITIZEN_PARKING_CARD.equalsIgnoreCase(type)
		|| RequestTypes.COMPANY_PARKING_CARD.equalsIgnoreCase(type)) {
	    documentService.createParkingCard(request);
	    documentService.createParkingCardDecision(true, request);
	    documentService.createPayment(request);
	}
    }

    private void reject(Request request) {
	String type = request.getTypeDescription();
	// TODO all this could be done much better
	if (RequestTypes.NATIONALITY_CERTIFICATE.equalsIgnoreCase(type)) {
	    documentService.createNationalityCertificate(false, request);
	} else if (RequestTypes.CITIZEN_PARKING_CARD.equalsIgnoreCase(type)
		|| RequestTypes.COMPANY_PARKING_CARD.equalsIgnoreCase(type)) {
	    documentService.createParkingCardDecision(false, request);
	}
    }

    private void createStatusEvent(Request updatedRequest, Request baseRequest) {
	RequestStatusInfo newStatusInfo = RequestStatusInfo.getStatusFor(updatedRequest.getStatus().getName());
	RequestStatusInfo oldStatusInfo = RequestStatusInfo.getStatusFor(baseRequest.getStatus().getName());
	boolean isCitizenInitiated = newStatusInfo.isCitizenInitiated(oldStatusInfo);
	long userAccountId;
	if (isCitizenInitiated) {
	    userAccountId = citizenService.findAccount(baseRequest.getCitizen().getId()).getAccountUserId();
	} else {
	    userAccountId = employeeService.findAccount(baseRequest.getAssignee().getNationalRegisterNb())
		    .getAccountUserId();
	}

	String comment = updatedRequest.getStatus() == null ? null : updatedRequest.getStatus().getComment();
	Event statusEvent = Event.create(newStatusInfo.getEventType(), userAccountId, updatedRequest.getId(), comment);

	eventService.create(statusEvent);
    }

    private Department findRequestDepartment(Request request) {
	Citizen citizen = request.getCitizen();
	Municipality citizenMunicipality = municipalityRepository.findByName(citizen.getAddress().getMunicipality());
	List<Department> municipalityDepartments = departmentRepository
		.findByMunicipalityId(citizenMunicipality.getId());

	for (Department department : municipalityDepartments) {
	    List<RequestType> departmentRequestTypes = department.getManagedRequestTypes();
	    for (RequestType requestType : departmentRequestTypes) {
		if (requestType.getDescription().equals(request.getTypeDescription())) {
		    return department;
		}
	    }
	}

	throw new BusinessException(FAILURE_DATABASE_RETRIEVAL,
		"The request type is not managed by any department of the municipality.");
    }

    private String generateSystemRef(Request request) {
	String citizenNRN = request.getCitizen().getNationalRegisterNb();
	String nbOfCreatedRequest = Integer
		.toString(requestRepository.findByCitizen(request.getCitizen().getId()).size());
	return "REQ_" + citizenNRN + "_" + nbOfCreatedRequest;
    }

    private String generateMunicipalityRef(Request request) {
	Municipality municipality = request.getDepartment().getMunicipality();
	String nbOfCreatedRequest = Integer.toString(requestRepository.findbyDepartmentId(municipality.getId()).size());
	return "REQ_" + municipality.getName().replaceAll("\\s+", "") + "_" + nbOfCreatedRequest;
    }
}
