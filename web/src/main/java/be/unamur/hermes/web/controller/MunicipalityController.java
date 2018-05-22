package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.MunicipalityService;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.entity.Municipality;

@RestController
@RequestMapping({ "/municipalities" })
public class MunicipalityController {

    private static Logger logger = LoggerFactory.getLogger(MunicipalityController.class);

    private final MunicipalityService municipalityService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService) {
	this.municipalityService = municipalityService;
    }

    // READ

    @GetMapping(params = { "!zipCode", "!addressId", "!name" })
    public ResponseEntity<List<Municipality>> getAllMunicipalities() {
	return ResponseEntity.ok(municipalityService.findAll());
    }

    @GetMapping
    public ResponseEntity<Municipality> getMunicipality(@RequestParam("zipCode") Optional<Long> zipCode,
	    @RequestParam("addressId") Optional<Long> addressId, @RequestParam("name") Optional<String> name) {
	if (zipCode.isPresent()) {
	    return ResponseEntity.ok(municipalityService.findByZIPcode(zipCode.get()));
	}
	if (addressId.isPresent())
	    return ResponseEntity.ok(municipalityService.findByAddress(addressId.get()));
	if (name.isPresent()) {
	    return ResponseEntity.ok(municipalityService.findByName(name.get()));
	}
	throw new BusinessException(Errors.FAILURE_RESTCALL_PARAMETERS, "No parameters are found!");
    }

    @GetMapping(path = "/{municipalityId}")
    public ResponseEntity<Municipality> getMunicipality(@PathVariable("municipalityId") long municipalityId) {
	return ResponseEntity.ok(municipalityService.findById(municipalityId));
    }

    // CREATE

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createMunicipality(@RequestBody Municipality municipality) {
	long newId = municipalityService.activate(municipality);
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newId).toUri();
	return ResponseEntity.created(location).build();
    }
}
