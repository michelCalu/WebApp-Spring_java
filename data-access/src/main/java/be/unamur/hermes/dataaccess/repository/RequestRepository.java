package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestStatus;

public interface RequestRepository {

    public static final String STATUS_NEW = "New";

    List<Request> findByCitizen(long citizenId);

    List<Request> findByCitizen(long citizenId, long requestTypeId);

    List<Request> findbyDepartmentId(long departmentId);

    List<Request> findbyAssigneeId(long assigneeId);

    List<Request> findByCompanyNb(String companyNb);

    List<Request> findByCompanyNb(String companyNb, long requestTypeId);

    Request findById(long id);

    long create(Request request);

    RequestStatus findRequestStatusById(long id);

    void updateStatus(Request request);

    void updateStatus(long requestId, String statusName);

    void updateAssignee(Request request);
}
