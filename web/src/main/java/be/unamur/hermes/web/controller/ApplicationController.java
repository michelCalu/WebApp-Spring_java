package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.EmployeeService;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/employees")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
	    employeeService.register(employee);
	    return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/employees")
    public ResponseEntity<List<Employee>> showPeople() {
	    return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @PostMapping(path = "/citizens")
    public ResponseEntity<Void> createEmployee(@RequestBody Citizen citizen) {
        citizenService.register(citizen);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/citizens")
    public ResponseEntity<List<Citizen>> showCitizens() {
        return ResponseEntity.status(HttpStatus.OK).body(citizenService.findAll());
    }

    @PutMapping(path = "/citizens")
    public ResponseEntity<Void> activateCitizen(@RequestBody Citizen citizen) {
        try{
            citizenService.activate(citizen);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (BusinessException be){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }
}
