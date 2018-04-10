package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee findByName(String firstname, String lastname);

    Employee findById(long employeeId);

    List<Employee> findAll();

    void create(Employee employee);

}
