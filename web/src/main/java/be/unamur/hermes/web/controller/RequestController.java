package be.unamur.hermes.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.RequestService;
import be.unamur.hermes.dataaccess.entity.Request;

@RestController
@RequestMapping({ "/requests" })
public class RequestController {

    private static Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
	this.requestService = requestService;
    }

    @GetMapping(path = "/{requestId}")
    public ResponseEntity<Request> getRequest(@PathVariable(value = "requestId") long requestId) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(requestService.find(requestId));
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Request>> getRequests(@RequestParam("citizenId") long citizenId) {
	try {
	    return ResponseEntity.status(HttpStatus.OK).body(requestService.findByCitizenId(citizenId));
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PostMapping
    public ResponseEntity<Void> createRequest(@RequestBody Request newRequest) {
	try {
	    Long requestId = requestService.create(newRequest);
	    return (requestId != null) ? ResponseEntity.status(HttpStatus.CREATED).build()
		    : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }
}
