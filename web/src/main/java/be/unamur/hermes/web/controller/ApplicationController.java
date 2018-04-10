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
import org.springframework.web.bind.annotation.RequestParam;

import be.unamur.hermes.business.parameters.ClaimType;
import be.unamur.hermes.business.service.ClaimService;
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.business.service.ParameterService;
import be.unamur.hermes.dataaccess.entity.Claim;

import java.util.List;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private ClaimService claimService;
    private ParameterService parameterService;

    @Autowired
    public ApplicationController(ClaimService claimService) {
	    this.claimService = claimService;
	this.parameterService = parameterService;
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

    @GetMapping(path = "/parameters/{municipality}")
    public ResponseEntity<ClaimType> getClaimType(@PathVariable(value = "municipality") String municipality,
	    @RequestParam("claimTypeId") String claimTypeId) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getClaimType(municipality, claimTypeId));
    }

    @GetMapping(path = "/parameters/{municipality}/all")
    public ResponseEntity<List<ClaimType>> getClaimTypes(@PathVariable(value = "municipality") String municipality) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getClaimTypes(municipality));
    }

    @GetMapping(path = "/parameters/{municipality}/{claimTypeId}")
    public ResponseEntity<String> getParameter(@PathVariable(value = "municipality") String municipality,
	    @PathVariable(value = "claimTypeId") String claimTypeId, @RequestParam("parameterId") String parameterId) {
	// e.g. to know if a type of claim is activated :
	// "/parameters/Gembloux/perm-parking?parameterId=activated"
	return ResponseEntity.status(HttpStatus.OK)
		.body(parameterService.getParameter(municipality, claimTypeId, parameterId));
    }
}
