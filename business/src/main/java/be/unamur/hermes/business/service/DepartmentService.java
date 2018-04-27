package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Skill;

import java.util.List;

public interface DepartmentService {

    Department findBySkill(Skill skill) throws BusinessException;

    List<Department> findAll(Municipality municipality);



}
