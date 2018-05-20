package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.MandataryService;
import be.unamur.hermes.common.enums.MandataryRole;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ "/mandataries" })
public class MandataryController {

    private final MandataryService mandataryService;

    @Autowired
    public MandataryController(MandataryService mandataryService) {
	this.mandataryService = mandataryService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Citizen citizen, Company company, MandataryRole role) {
	long newId = mandataryService.create(citizen, company, role);
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newId).toUri();
	return ResponseEntity.created(location).build();
    }

    // READ
    @GetMapping(path = "/{mandataryId}")
    public ResponseEntity<Mandatary> findById(@PathVariable(value = "mandataryId") long mandataryId) {
	Mandatary mandatary = mandataryService.findById(mandataryId);
	return ResponseEntity.ok(mandatary);
    }

    @GetMapping
    public ResponseEntity<List<Mandatary>> find(@RequestParam("companyNb") Optional<String> companyNb,
	    @RequestParam("role") Optional<String> role, @RequestParam("citizenId") Optional<Long> citizenId,
	    @RequestParam("companyStatus") Optional<String> companyStatus) {
	if (citizenId.isPresent())
	    return ResponseEntity.ok(mandataryService.findByCitizen(citizenId.get(), companyStatus));
	if (companyNb.isPresent()) {
	    if (role.isPresent())
		return ResponseEntity.ok(mandataryService.findByCompany(companyNb.get(), role.get()));
	    return ResponseEntity.ok(mandataryService.findByCompany(companyNb.get()));
	}
	return ResponseEntity.ok(mandataryService.findAll());
    }
}
