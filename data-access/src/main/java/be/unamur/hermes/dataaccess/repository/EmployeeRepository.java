package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Employee;

public interface EmployeeRepository {

    Employee findByName(String firstname, String lastname);

    Employee findById(long employeeId);

    void create(String firstname, String lastname);

}
