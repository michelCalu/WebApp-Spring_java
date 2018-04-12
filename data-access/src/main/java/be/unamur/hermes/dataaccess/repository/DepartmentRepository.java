package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Department;

public interface DepartmentRepository {

    public long create();

    public Department findById(long id);

}
