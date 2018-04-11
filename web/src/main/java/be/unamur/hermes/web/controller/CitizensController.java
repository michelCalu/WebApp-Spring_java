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
        try {
            citizenService.register(citizen);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Citizen>> showCitizens() {
        return ResponseEntity.status(HttpStatus.OK).body(citizenService.findAll());
    }

    @GetMapping(path = "/{citizenID}")
    public ResponseEntity<Citizen> showCitizenById(@PathVariable(value = "citizenID") long citizenID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(citizenService.findById(citizenID));
        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/{lastName}/{firstName}")
    public ResponseEntity<Citizen> showCitizenByName(
            @PathVariable(value = "lastName") String lastName,
            @PathVariable(value = "firstName") String firstName) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(citizenService.findByName(firstName,lastName));
        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Citizen>> showPendingCitizens(){
        return ResponseEntity.status(HttpStatus.OK).body(citizenService.findPending());
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
