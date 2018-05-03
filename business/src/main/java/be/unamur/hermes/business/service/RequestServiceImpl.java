package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.repository.RequestFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.RequestType;
import be.unamur.hermes.dataaccess.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestFieldRepository requestFieldRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestFieldRepository requestFieldRepository) {
	super();
	this.requestRepository = requestRepository;
	this.requestFieldRepository = requestFieldRepository;
    }

    @Override
    public Request find(long requestId) {
	return requestRepository.findById(requestId);
    }

    @Override
    public long create(Request newRequest) {
	// TODO validate with Authentification
	RequestType requestType = newRequest.getType();
	if (findRequestTypeById(requestType.getId()) == null)
	    throw new BusinessException("Unknown request type:" + requestType.getDescription());
	for(RequestField field : newRequest.getData()){
	    if(field.getFieldType().equals("String") && field.getFile() != null)
	        throw new BusinessException("Field type doesn't match field content ! Expected 'String' got 'File'");
	    if(field.getFieldType().equals("File") && field.getValue() != null)
	        throw new BusinessException("Field type doesn't match field content ! Expected 'File' got 'String'");
	    if(field.getValue() == null && field.getFile() == null)
	        throw new BusinessException("No value or file is attached to this field !");
	    requestFieldRepository.createRequestField(field);
	}
	return requestRepository.create(newRequest);
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
}
