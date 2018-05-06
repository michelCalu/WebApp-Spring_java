package be.unamur.hermes.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.business.service.UserAccountService;
import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.Citizen;

@RestController
@RequestMapping({ "/citizens" })
public class CitizensController {

    private final CitizenService citizenService;
    private final UserAccountService userAccountService;

    @Autowired
    public CitizensController(CitizenService citizenService, UserAccountService userAccountService) {
	this.citizenService = citizenService;
	this.userAccountService = userAccountService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Object> createCitizen(@RequestBody Citizen citizen) throws URISyntaxException {
	try {
	    long id = citizenService.register(citizen);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
	    return ResponseEntity.created(location).build();
	} catch (BusinessException be) {
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }

    // READ

    @GetMapping
    public ResponseEntity<List<Citizen>> showCitizens() {
	return new ResponseEntity<>(citizenService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{citizenID}")
    public ResponseEntity<Object> showCitizenById(@PathVariable(value = "citizenID") long citizenID) {
	try {
	    return new ResponseEntity<>(citizenService.findById(citizenID), HttpStatus.OK);
	} catch (BusinessException be) {
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }

    @GetMapping(path = "/{lastName}/{firstName}")
    public ResponseEntity<Object> showCitizenByName(@PathVariable(value = "lastName") String lastName,
	    @PathVariable(value = "firstName") String firstName) {
	try {
	    return new ResponseEntity<>(citizenService.findByName(firstName, lastName), HttpStatus.OK);
	} catch (BusinessException be) {
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Citizen>> showPendingCitizens() {
	return new ResponseEntity<>(citizenService.findPending(), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping(path = "/account/{citizenID}")
    public ResponseEntity<Object> updateAccount(@RequestBody UpdateUserAccount accountUpdate,
	    @PathVariable("citizenID") Long citizenID) {
	try {
	    userAccountService.updateCitizenAccount(citizenID, accountUpdate);
	    return new ResponseEntity<Object>(HttpStatus.OK);
	} catch (BusinessException be) {
	    return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
    }

    @PatchMapping(path = "/{citizenID}")
    public ResponseEntity<Citizen> updateCitizen(@RequestBody Map<String, Object> updates,
	    @PathVariable("citizenID") Long citizenID) {
	try {
	    citizenService.update(citizenID, updates);
	    Citizen updatedCitizen = citizenService.findById(citizenID);
	    return new ResponseEntity<Citizen>(updatedCitizen, HttpStatus.OK);
	} catch (BusinessException be) {
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }
}
