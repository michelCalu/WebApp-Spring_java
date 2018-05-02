package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Skill;
import be.unamur.hermes.dataaccess.repository.DepartmentRepository;

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
    public List<Department> findAll(long municipalityID) {
	return departmentRepository.findByMunicipalityId(municipalityID);
    }

    @Override
    public Department findById(long departmentId) {
	return departmentRepository.findById(departmentId);
    }
}
