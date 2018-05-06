package be.unamur.hermes.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.service.CompanyService;
import be.unamur.hermes.dataaccess.entity.Company;

@RestController
@RequestMapping({ "/companies" })
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
	this.companyService = companyService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Company company) {
	String companyNb = companyService.create(company);
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(companyNb)
		.toUri();
	return ResponseEntity.created(location).build();
    }

    // READ
    @GetMapping(path = "/{companyNb}")
    public ResponseEntity<Company> findById(@PathVariable(value = "companyNb") String companyNb) {
	Company company = companyService.findByCompanyNumber(companyNb);
	return ResponseEntity.ok(company);
    }
}
