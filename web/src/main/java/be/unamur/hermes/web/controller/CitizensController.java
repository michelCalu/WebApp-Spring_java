package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.dataaccess.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/citizens"})
public class CitizensController {

    private final CitizenService citizenService;

    @Autowired
    public CitizensController(CitizenService citizenService){
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<Void> createCitizen(@RequestBody Citizen citizen) {
        citizenService.register(citizen);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Citizen>> showCitizens() {
        return ResponseEntity.status(HttpStatus.OK).body(citizenService.findAll());
    }

    @PutMapping
    public ResponseEntity<Citizen> activateCitizen(@RequestBody Citizen citizen) {
        try{
            Citizen updatedCitizen = citizenService.activate(citizen);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCitizen);
        } catch (BusinessException be){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
