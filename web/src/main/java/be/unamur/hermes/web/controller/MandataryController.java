package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.service.MandataryService;
import be.unamur.hermes.dataaccess.entity.Mandatary;

@RestController
@RequestMapping({ "/mandataries" })
public class MandataryController {

    private final MandataryService mandataryService;

    @Autowired
    public MandataryController(MandataryService mandataryService) {
	this.mandataryService = mandataryService;
    }

    // CREATE
    @PreAuthorize("hasRole('ROLE_ADMIN') or #mandatary.citizen.id == principal.technicalId")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Mandatary mandatary) {
	long newId = mandataryService.register(mandatary);
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
