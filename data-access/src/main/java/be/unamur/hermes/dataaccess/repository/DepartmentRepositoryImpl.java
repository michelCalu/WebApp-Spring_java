package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Override
    public long create() {
        return 0;
    }

    @Override
    public Department findById(long id) {
        return null;
    }

}
