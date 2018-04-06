package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findByName(String firstName, String lastname);

    Employee findById(Long employeeId);

    List<Employee> findAll();

    void register(String firstname, String lastname);
}
