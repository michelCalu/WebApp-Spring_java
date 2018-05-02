package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Skill;

public interface DepartmentService {

    Department findBySkill(Skill skill) throws BusinessException;

    List<Department> findAll(long municipalityID);

    Department findById(long departmentId);

}
