package be.unamur.hermes.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.ClaimService;
import be.unamur.hermes.dataaccess.entity.Claim;

@RestController
@RequestMapping({ "/claims" })
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
	this.claimService = claimService;
    }

    @GetMapping(path = "/{claimId}")
    public ResponseEntity<Claim> getClaim(@PathVariable(value = "claimId") long claimId) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
	} catch (Exception ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Claim>> getClaims(@RequestParam("citizenId") long citizenId) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(claimService.findByCitizenId(citizenId));
	} catch (Exception ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PutMapping
    public ResponseEntity<Void> createClaim(@RequestBody Claim newClaim) {
	try {
	    Long claimId = claimService.create(newClaim);
	    return (claimId != null) ? ResponseEntity.status(HttpStatus.CREATED).build()
		    : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }
}
