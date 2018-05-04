package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestType;

public interface RequestService {

    public static final String STATUS_ACCEPTED = "accepted";
    public static final String STATUS_REJECTED = "rejected";
    public static final String TYPE_NATIONALITY_CERTIFICATE = "nationalityCertificate";
    public static final String TYPE_PARKING_CARD = "citizenParkingCard";

    Request find(long requestId);

    List<Request> findByCitizenId(long citizenId);

    List<Request> findByDepartmentId(long departmentId);

    List<Request> findByAssigneeId(long assigneeId);

    List<Request> find(long citizenId, long requestTypeId);

    long create(Request newRequest);

    RequestType findRequestTypeByDescription(String description);

    RequestType findRequestTypeById(long id);

    void update(Request request) throws BusinessException;

}
