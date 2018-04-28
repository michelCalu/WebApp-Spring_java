package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.CreateRequest;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestType;

public interface RequestService {

    Request find(long claimId);

    List<Request> findByCitizenId(long citizenId);

    List<Request> findByDepartmentId(long departmentId);

    List<Request> findByAssigneeId(long assigneeId);

    List<Request> find(long citizenId, long requestTypeId);

    long create(CreateRequest newClaim);

    RequestType findRequestTypeByDescription(String description);

    RequestType findRequestTypeById(long id);

}
