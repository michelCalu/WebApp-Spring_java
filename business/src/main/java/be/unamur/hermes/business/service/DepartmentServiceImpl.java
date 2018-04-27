package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Skill;
import be.unamur.hermes.dataaccess.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department findBySkill(Skill skill) throws BusinessException {
        return null;
    }

    @Override
    public List<Department> findAll(Municipality municipality) {
        return null;
    }
}
