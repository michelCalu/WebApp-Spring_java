package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.dataaccess.entity.Employee;

@RestController
@RequestMapping({ "/employees" })
public class EmployeesController {

    private static Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
	this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
	try {
	    long newId = employeeService.register(employee);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newId)
		    .toUri();
	    return ResponseEntity.created(location).build();
	} catch (BusinessException ex) {
	    logger.error("Request createEmployee failed", ex);
	    return ResponseEntity.badRequest().build();
	}
    }

    @GetMapping
    public ResponseEntity<List<Employee>> showEmployees() {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
	} catch (BusinessException be) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<Employee> showEmployeeById(@PathVariable(value = "employeeID") long employeeID) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(employeeID));
	} catch (BusinessException be) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @GetMapping(path = "/{lastName}/{firstName}")
    public ResponseEntity<Employee> showEmployeeByName(@PathVariable(value = "lastName") String lastName,
	    @PathVariable(value = "firstName") String firstName) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(employeeService.findByName(firstName, lastName));
	} catch (BusinessException be) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

}
