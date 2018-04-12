package be.unamur.hermes.web.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private final ServletContext servletContext;

    @Autowired
    public RequestController(RequestService requestService, ServletContext servletContext) {
	this.requestService = requestService;
	this.servletContext = servletContext;
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
    public ResponseEntity<List<Request>> getRequests(@RequestParam("citizenId") long citizenId,
	    @RequestParam("requestTypeId") Optional<Long> requestTypeId) {
	try {
	    List<Request> data = requestTypeId.isPresent() ? requestService.find(citizenId, requestTypeId.get())
		    : requestService.findByCitizenId(citizenId);
	    return ResponseEntity.status(HttpStatus.OK).body(data);
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PostMapping
    public ResponseEntity<Void> createRequest(@RequestBody Request newRequest, HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    Long requestId = requestService.create(newRequest);
	    if (requestId != null) {
		ResponseEntity<Void> result = ResponseEntity.status(HttpStatus.CREATED).build();
		response.addHeader("Location", String.format("/requests/{%s}", String.valueOf(requestId)));
		return result;
	    }
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }
}
