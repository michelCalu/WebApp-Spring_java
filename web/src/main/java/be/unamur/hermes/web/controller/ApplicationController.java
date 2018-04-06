package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Inhabitant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.ClaimService;
import be.unamur.hermes.business.service.InhabitantService;
import be.unamur.hermes.dataaccess.entity.Claim;

import java.util.List;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private InhabitantService inhabitantService;
    private EmployeeService employeeService;
    private ClaimService claimService;

    @Autowired
    public ApplicationController(InhabitantService inhabitantService, EmployeeService employeeService,
	    ClaimService claimService) {
	this.inhabitantService = inhabitantService;
	this.employeeService = employeeService;
	this.claimService = claimService;
    }

    @GetMapping
    public String serveHomePage() {
	return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
	    return "about";
    }

    @PostMapping(path = "/createEmployee")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
	    employeeService.register(employee.getFirstName(), employee.getLastName());
	    return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showEmployees")
    public ResponseEntity<List<Employee>> showPeople() {
	    return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @PostMapping(path = "/createInhabitant")
    public ResponseEntity<Void> createEmployee(@RequestBody Inhabitant inhabitant) {
        inhabitantService.register(inhabitant.getFirstName(), inhabitant.getLastName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showInhabitants")
    public ResponseEntity<List<Inhabitant>> showInhabitants() {
        return ResponseEntity.status(HttpStatus.OK).body(inhabitantService.findAll());
    }

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }
}
