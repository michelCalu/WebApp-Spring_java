package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;

public interface RequestService {

    Request find(long claimId);

    List<Request> findByCitizenId(long citizenId);

    List<Request> find(long citizenId, long requestTypeId);

    long create(Request newClaim);

}
