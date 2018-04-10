package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Claim;
import be.unamur.hermes.dataaccess.repository.ClaimRepository;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository) {
	super();
	this.claimRepository = claimRepository;
    }

    @Override
    public Claim find(long claimId) {
	return claimRepository.findById(claimId);
    }

    @Override
    public long create(Claim newClaim) {
	if (newClaim.getId() != null)
	    throw new BusinessException("Claim is already registered in the database !");
	return claimRepository.create(newClaim);
    }

    @Override
    public List<Claim> findByCitizenId(long citizenId) {
	return claimRepository.findByCitizen(citizenId);
    }
}
