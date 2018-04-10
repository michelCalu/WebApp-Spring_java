package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.exception.BusinessException;
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> showEmployees() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
        } catch(BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<Employee> showEmployeeById(@PathVariable(value = "employeeID") long employeeID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(employeeID));
        } catch(BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/{lastName}/{firstName}")
    public ResponseEntity<Employee> showEmployeeByName(
            @PathVariable(value = "lastName") String lastName,
            @PathVariable(value = "firstName") String firstName
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.findByName(firstName, lastName));
        } catch(BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
