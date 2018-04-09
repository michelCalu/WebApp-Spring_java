package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.dataaccess.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/employees"})
public class EmployeesController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        employeeService.register(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> showPeople() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }
}
