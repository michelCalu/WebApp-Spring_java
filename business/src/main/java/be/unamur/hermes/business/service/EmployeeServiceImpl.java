package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee find(String firstName, String lastname){
        return employeeRepository.findByName(firstName,lastname);
    }

    @Override
    public void register(String firstname, String lastname){
        employeeRepository.create(firstname,lastname);
    }
}
