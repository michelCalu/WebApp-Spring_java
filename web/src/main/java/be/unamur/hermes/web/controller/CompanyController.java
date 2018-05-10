package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.CompanyService;
import be.unamur.hermes.dataaccess.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
	long companyNb = companyService.register(company );
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(companyNb)
		.toUri();
	return ResponseEntity.created(location).build();
    }

    // READ
    @GetMapping(path = "/{companyNb}")
    public ResponseEntity<Company> findById(@PathVariable(value = "companyNb") String companyNb) {
	Company company = companyService.findByCompanyNb(companyNb);
	return ResponseEntity.ok(company);
    }
}
