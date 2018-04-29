package be.unamur.hermes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
	this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByName(String firstName, String lastname) throws BusinessException {
	try {
	    return employeeRepository.findByName(firstName, lastname);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Employee not found !");
	}
    }

    @Override
    public Employee findById(Long employeeId) throws BusinessException {
	try {
	    return employeeRepository.findById(employeeId);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Employee not found !");
	}
    }

    @Override
    public List<Employee> findAll() {
	return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void register(Employee employee) {
	employeeRepository.create(employee);
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) throws BusinessException {
	try {
	    return employeeRepository.findAccount(nationalRegistrationNb);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Account not found !");
	}
    }
}
