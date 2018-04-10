package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Claim;

public interface ClaimService {

    Claim find(long claimId);

    List<Claim> findByCitizenId(long citizenId);

    long create(Claim newClaim);

}
