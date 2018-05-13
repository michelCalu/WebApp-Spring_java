package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.constants.EventConstants;
import be.unamur.hermes.common.enums.RequestStatusInfo;
import be.unamur.hermes.dataaccess.entity.*;
import be.unamur.hermes.dataaccess.repository.DepartmentRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.RequestFieldRepository;
import be.unamur.hermes.dataaccess.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
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

    // rollback for all exceptions, not only runtime exceptions and errors
    @Transactional(rollbackFor = Exception.class)
    @Override
    public long create(Request newRequest, Map<String, MultipartFile> codeToFiles) {
	// TODO validate with Authentification
	// Setting basic info
	RequestType requestType = findRequestTypeByDescription(newRequest.getTypeDescription());
	newRequest.setType(requestType);
	if (requestType == null)
	    throw new BusinessException("Unknown request type:" + newRequest.getTypeDescription());

	Department department = findRequestDepartment(newRequest);
	newRequest.setDepartment(department);
	newRequest.setSystemRef(generateSystemRef(newRequest));
	newRequest.setMunicipalityRef(generateMunicipalityRef(newRequest));

	Long newRequestId = requestRepository.create(newRequest);

	for (RequestField field : newRequest.getData()) {
	    if (field.getFieldType().equals("String") && field.getFieldFile() != null)
		throw new BusinessException("Field type doesn't match field content ! Expected 'String' got 'File'");
	    if (field.getFieldType().equals("File") && field.getFieldValue() != null)
		throw new BusinessException("Field type doesn't match field content ! Expected 'File' got 'String'");
	    if (field.getFieldValue() == null && field.getFieldFile() == null)
		throw new BusinessException("No value or file is attached to this field !");
	    requestFieldRepository.createRequestField(field, newRequestId);
	}

	try {
	    for (String code : codeToFiles.keySet()) {
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
    @Transactional(rollbackFor = Exception.class)
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
	    // for all statusses : update status ...
	    requestRepository.updateStatus(updatedRequest);
	    // ... and create event
	    createStatusEvent(updatedRequest, baseRequest);
	    return;
	}

	if (updatedRequest.getAssignee() != null) {
	    requestRepository.updateAssignee(updatedRequest);
	    long userAccountId = employeeService.findAccount(updatedRequest.getAssignee().getId()).getAccountUserId();
	    Event statusEvent = Event.create(EventConstants.TYPE_ASSIGNEE_CHANGE, userAccountId,
		    updatedRequest.getId());
	    eventService.create(statusEvent);
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

	Event statusEvent = Event.create(newStatusInfo.getEventType(), userAccountId, updatedRequest.getId());
	eventService.create(statusEvent);
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

    private String generateMunicipalityRef(Request request) {
	Municipality municipality = request.getDepartment().getMunicipality();
	String nbOfCreatedRequest = Integer.toString(requestRepository.findbyDepartmentId(municipality.getId()).size());
	return "REQ_" + municipality.getName().replaceAll("\\s+", "") + "_" + nbOfCreatedRequest;
    }
}
