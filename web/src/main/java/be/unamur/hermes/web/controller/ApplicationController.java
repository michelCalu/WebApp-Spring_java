package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Employee;
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
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.dataaccess.entity.Claim;

import java.util.List;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private CitizenService citizenService;
    private EmployeeService employeeService;
    private ClaimService claimService;

    @Autowired
    public ApplicationController(CitizenService citizenService, EmployeeService employeeService,
                                 ClaimService claimService) {
	this.citizenService = citizenService;
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

    @PostMapping(path = "/createCitizen")
    public ResponseEntity<Void> createEmployee(@RequestBody Citizen citizen) {
        citizenService.register(citizen.getFirstName(), citizen.getLastName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showCitizens")
    public ResponseEntity<List<Citizen>> showCitizens() {
        return ResponseEntity.status(HttpStatus.OK).body(citizenService.findAll());
    }

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }
}
