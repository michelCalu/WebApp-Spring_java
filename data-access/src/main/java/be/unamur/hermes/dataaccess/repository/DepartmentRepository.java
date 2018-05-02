package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Department;

public interface DepartmentRepository {

    public long create(Department department);

    public Department findById(long id);

    public List<Department> findByMunicipalityId(long munId);

}
