package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestStatus;
import be.unamur.hermes.dataaccess.entity.RequestType;

public interface RequestRepository {

    public static final String STATUS_NEW = "New";

    List<Request> findByCitizen(long citizenId);

    List<Request> findByCitizen(long citizenId, long requestTypeId);

    List<Request> findbyDepartmentId(long departmentId);

    List<Request> findbyAssigneeId(long assigneeId);

    Request findById(long id);

    long create(Request newClaim);

    RequestType findRequestTypeByDescription(String description);

    RequestType findRequestTypeById(long id);

    RequestStatus findRequestStatusById(long id);
}
