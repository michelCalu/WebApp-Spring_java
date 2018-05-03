package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.service.DocumentService;
import be.unamur.hermes.business.service.RequestService;
import be.unamur.hermes.dataaccess.entity.CreateRequest;
import be.unamur.hermes.dataaccess.entity.Request;

@RestController
@RequestMapping({ "/requests" })
public class RequestController {

    private static Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;
    private final DocumentService documentService;

    @Autowired
    public RequestController(RequestService requestService, DocumentService documentService) {
	this.requestService = requestService;
	this.documentService = documentService;
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

    @GetMapping
    public ResponseEntity<List<Request>> getRequests(@RequestParam("citizenId") Optional<Long> citizenId,
	    @RequestParam("requestTypeId") Optional<Long> requestTypeId,
	    @RequestParam("departmentId") Optional<Long> departmentId,
	    @RequestParam("assigneeId") Optional<Long> assigneeId) {
	try {
	    List<Request> data = null;
	    if (departmentId.isPresent()) {
		data = requestService.findByDepartmentId(departmentId.get());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	    }
	    if (assigneeId.isPresent()) {
		data = requestService.findByAssigneeId(assigneeId.get());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	    }
	    if (!citizenId.isPresent())
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    data = requestTypeId.isPresent() ? requestService.find(citizenId.get(), requestTypeId.get())
		    : requestService.findByCitizenId(citizenId.get());
	    return ResponseEntity.status(HttpStatus.OK).body(data);
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PostMapping
    public ResponseEntity<Void> createRequest(@RequestBody CreateRequest newRequest, HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    long requestId = requestService.create(newRequest);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(requestId)
		    .toUri();
	    return ResponseEntity.created(location).build();
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

}
