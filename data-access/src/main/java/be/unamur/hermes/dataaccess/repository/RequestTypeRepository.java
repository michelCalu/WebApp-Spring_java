package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.RequestType;

import java.util.List;

public interface RequestTypeRepository {

    RequestType findById(Long id);

    RequestType findByDescription(String description);

    List<RequestType> findByDepartmentId(Long departmentId);

    List<RequestType> findByMunicipalityId(Long municipalityId);

}
