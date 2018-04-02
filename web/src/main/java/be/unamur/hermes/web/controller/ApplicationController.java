package be.unamur.hermes.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.ClaimService;
import be.unamur.hermes.business.service.InhabitantService;
import be.unamur.hermes.business.service.PeopleBisService;
import be.unamur.hermes.dataaccess.entity.Claim;
import be.unamur.hermes.dataaccess.entity.PeopleBis;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private InhabitantService inhabitantService;
    private PeopleBisService peopleBisService;
    private ClaimService claimService;

    @Autowired
    public ApplicationController(InhabitantService inhabitantService, PeopleBisService peopleBisService,
	    ClaimService claimService) {
	this.inhabitantService = inhabitantService;
	this.peopleBisService = peopleBisService;
	this.claimService = claimService;
    }

    @GetMapping
    public String serveHomePage() {
	return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
	System.out.println("About bitches");
	inhabitantService.register("Marco", "Polo");
	return "about";
    }

    @PostMapping(path = "/createpeople")
    public ResponseEntity<Void> createPeople(@RequestBody PeopleBis peopleBis) {
	inhabitantService.register(peopleBis.getFirstName(), peopleBis.getLastName());
	return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showpeople")
    public ResponseEntity<List<PeopleBis>> showPeople() {
	return ResponseEntity.status(HttpStatus.OK).body(peopleBisService.findAll());
    }

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }
}
