package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.business.service.CompanyService;
import be.unamur.hermes.business.service.MandataryService;
import be.unamur.hermes.common.enums.MandataryRole;
import be.unamur.hermes.dataaccess.dto.UpdateCompanyAccount;
import be.unamur.hermes.dataaccess.entity.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({ "/companies" })
public class CompanyController {

    private final CompanyService companyService;
    private final CitizenService citizenService;
    private final MandataryService mandataryService;
    private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyController(CompanyService companyService, CitizenService citizenService, MandataryService mandataryService) {
	this.companyService = companyService;
	this.mandataryService = mandataryService;
	this.citizenService = citizenService;
    }

    // CREATE
    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestBody Company company) {
        try {
            companyService.register(company);
            mandataryService.create(citizenService.findById(company.getCreatorId()), company, MandataryRole.OWNER);
            URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{companyNb}").buildAndExpand(company.getCompanyNb())
                    .toUri();
            return ResponseEntity.created(location).build();
        }catch (BusinessException ex) {
            logger.error("Create company failed", ex);
            throw new BusinessException("Create company failed"+ex);
        }
    }

    // READ
    @GetMapping(path = "/{companyNb}")
    public ResponseEntity<Company> findById(@PathVariable(value = "companyNb") String companyNb) {
        Company company = companyService.findByCompanyNb(companyNb);
        return ResponseEntity.ok(company);
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Company>> getPending(@RequestParam("municipalityID") long municipalityID) {
        try {
            List<Company> comps = companyService.findPending(municipalityID);
            return ResponseEntity.ok(comps);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        }
    }

    //activate
    @PatchMapping(path = "/{companyNb}")
    public ResponseEntity<Company> updateCompany(@RequestBody UpdateCompanyAccount updates,
                                                 @PathVariable("companyNb") String companyNb) {
        try {
            companyService.activate(companyNb, updates);
            Company updatedCompany = companyService.findByCompanyNb(companyNb);
            return new ResponseEntity<Company>(updatedCompany, HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
