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

    private ClaimService claimService;

    @Autowired
    public ApplicationController(ClaimService claimService) {
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

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }
}
