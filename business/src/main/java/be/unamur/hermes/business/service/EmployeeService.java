package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface EmployeeService {

    Employee findByName(String firstName, String lastname);

    Employee findById(Long employeeId);

    UserAccount findAccount(String nationalRegistrationNb) throws BusinessException;

    List<Employee> findAll();

    void register(Employee employee);
}
