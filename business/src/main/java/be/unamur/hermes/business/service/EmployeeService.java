package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Employee;

public interface EmployeeService {

    Employee find(String firstName, String lastname);

    void register(String firstname, String lastname);
}
