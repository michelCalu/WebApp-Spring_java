package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.ClaimStatus;
import be.unamur.hermes.dataaccess.entity.Request;
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
    public long create(Request newRequest) {
	if (newRequest.getId() != null)
	    throw new BusinessException("Request is already registered in the database !");
	int statusId = newRequest.getStatus();
	ClaimStatus status = ClaimStatus.getStatus(statusId);
	if (status == null)
	    throw new BusinessException("Invalid request status: '" + statusId + "'");
	return requestRepository.create(newRequest);
    }

    @Override
    public List<Request> findByCitizenId(long citizenId) {
	return requestRepository.findByCitizen(citizenId);
    }
}
