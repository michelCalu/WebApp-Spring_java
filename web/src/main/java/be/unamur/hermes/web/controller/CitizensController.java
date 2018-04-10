package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.exception.NRNNotValidException;
import be.unamur.hermes.business.service.CitizenService;
import be.unamur.hermes.dataaccess.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping({"/citizens"})
public class CitizensController {

    private final CitizenService citizenService;

    @Autowired
    public CitizensController(CitizenService citizenService){
        this.citizenService = citizenService;
    }

    // CREATE

    @PostMapping
    public ResponseEntity<Object> createCitizen(@RequestBody Citizen citizen)throws URISyntaxException {
        try {
            long id = citizenService.register(citizen);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(new URI("/citizens/" + id));
            return new ResponseEntity<>(responseHeaders,HttpStatus.CREATED);
        } catch (BusinessException be) {
            return new ResponseEntity<>(be, HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(be, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{lastName}/{firstName}")
    public ResponseEntity<Object> showCitizenByName(
            @PathVariable(value = "lastName") String lastName,
            @PathVariable(value = "firstName") String firstName) {
        try {
            return new ResponseEntity<>(citizenService.findByName(firstName,lastName), HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity<>(be, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<List<Citizen>> showPendingCitizens(){
        return new ResponseEntity<>(citizenService.findPending(), HttpStatus.OK);
    }

    @GetMapping(path = "/validNRN/{citizenID}")
    public ResponseEntity isNRNValid(@PathVariable(value = "citizenID") long citizenID) {
        /*try{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(citizenService.validateNRN(citizenID));
        } catch (NRNNotValidException nrnE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(nrnE.getMessage());
        }*/
        return null;
    }

    // UPDATE

    @PutMapping
    public ResponseEntity<Object> activateCitizen(@RequestBody Citizen citizen) {
        try{
            Citizen updatedCitizen = citizenService.activate(citizen);
            return new ResponseEntity<>(updatedCitizen, HttpStatus.OK);
        } catch (BusinessException be){
            return new ResponseEntity<>(be, HttpStatus.BAD_REQUEST);
        }
    }
}
