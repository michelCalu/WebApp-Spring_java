package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.CreateRequest;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestType;

public interface RequestRepository {

    List<Request> findByCitizen(long citizenId);

    List<Request> findByCitizen(long citizenId, long requestTypeId);

    List<Request> findbyDepartmentId(long departmentId);

    Request findById(long id);

    long create(CreateRequest newClaim);

    RequestType findRequestTypeByDescription(String description);

    RequestType findRequestTypeById(long id);
}
