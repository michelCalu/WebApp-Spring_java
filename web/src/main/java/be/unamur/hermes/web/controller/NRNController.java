package be.unamur.hermes.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.business.service.NRNService;
import be.unamur.hermes.common.exception.NRNNotValidException;

@RestController
@RequestMapping({ "/nrn" })
public class NRNController {

    private final NRNService nRNService;

    @Autowired
    NRNController(NRNService nRNService) {
	this.nRNService = nRNService;
    }

    @GetMapping(path = "/validation/{nrn}")
    public ResponseEntity<Object> isNRNValid(@PathVariable(value = "nrn") String nrn) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(nRNService.isNRNValid(nrn));
	} catch (NRNNotValidException | NRNServiceAccessException nrnE) {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(nrnE.getMessage());
	}
    }

    @GetMapping(path = "/{nrn}")
    public ResponseEntity<Object> showNRNValidationModel(@PathVariable(value = "nrn") String nrn)
	    throws NRNServiceAccessException {
	return ResponseEntity.status(HttpStatus.OK).body(nRNService.getNRNValidationModel(nrn));
    }

}
