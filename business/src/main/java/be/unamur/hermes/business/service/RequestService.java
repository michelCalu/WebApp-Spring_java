package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.*;

public interface RequestService {

    Request find(long requestId);

    List<Request> findByCitizenId(long citizenId);

    List<Request> findByDepartmentId(long departmentId);

    List<Request> findByAssigneeId(long assigneeId);

    List<Request> find(long citizenId, long requestTypeId);

    long create(Request newRequest);
    
    RequestType findRequestTypeByDescription(String description);

    RequestType findRequestTypeById(long id);

}
