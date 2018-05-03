package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.RequestStatus;

public interface RequestStatusRepository {

    public RequestStatus getStatusByName(String name);

    public RequestStatus getStatusById(Long id);

}
