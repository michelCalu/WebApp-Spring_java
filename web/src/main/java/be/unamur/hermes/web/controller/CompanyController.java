package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.service.CompanyService;
import be.unamur.hermes.dataaccess.dto.UpdateCompanyAccount;
import be.unamur.hermes.dataaccess.entity.Company;

@RestController
@RequestMapping({ "/companies" })
public class CompanyController {

    private final CompanyService companyService;
    private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyController(CompanyService companyService) {
	this.companyService = companyService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Company company) {
	companyService.register(company);
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{companyNb}")
		.buildAndExpand(company.getCompanyNb()).toUri();
	return ResponseEntity.created(location).build();
    }

    // READ
    @GetMapping(path = "/{companyNb}")
    public ResponseEntity<Company> findById(@PathVariable(value = "companyNb") String companyNb) {
	Company company = companyService.findByCompanyNb(companyNb);
	return ResponseEntity.ok(company);
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Company>> getPending(@RequestParam("municipalityID") long municipalityID) {
	List<Company> comps = companyService.findPending(municipalityID);
	return ResponseEntity.ok(comps);
    }

    // activate
    @PatchMapping(path = "/{companyNb}")
    public ResponseEntity<Company> updateCompany(@RequestBody UpdateCompanyAccount updates,
	    @PathVariable("companyNb") String companyNb) {
	companyService.activate(companyNb, updates);
	Company updatedCompany = companyService.findByCompanyNb(companyNb);
	return new ResponseEntity<Company>(updatedCompany, HttpStatus.OK);
    }
}
