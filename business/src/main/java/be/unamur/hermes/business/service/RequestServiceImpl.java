package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.ClaimStatus;
import be.unamur.hermes.dataaccess.entity.CreateRequest;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestType;
import be.unamur.hermes.dataaccess.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository claimRepository) {
	super();
	this.requestRepository = claimRepository;
    }

    @Override
    public Request find(long requestId) {
	return requestRepository.findById(requestId);
    }

    @Override
    public long create(CreateRequest newRequest) {
	// TODO validate with Authentification
	Integer statusId = newRequest.getStatus();
	if (statusId == null) {
	    newRequest.setStatus(ClaimStatus.NEW.getId());
	}
	String type = newRequest.getType();
	if (!StringUtils.hasText(type))
	    throw new BusinessException("Request type is mandatory");
	RequestType requestType = requestRepository.findRequestTypeByDescription(type);
	if (requestType == null)
	    throw new BusinessException("Unknown request type:" + type);
	newRequest.setRequestTypeId(requestType.getRequestTypeId());
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
}
