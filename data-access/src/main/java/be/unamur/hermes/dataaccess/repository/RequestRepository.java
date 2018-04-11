package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;

public interface RequestRepository {

    List<Request> findByCitizen(long citizenId);

    Request findById(long id);

    long create(Request newClaim);

}
