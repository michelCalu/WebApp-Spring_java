package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.exception.BusinessException;
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

    @GetMapping
    public ResponseEntity<List<Request>> getRequests(@RequestParam("citizenId") Optional<Long> citizenId,
	    @RequestParam("requestTypeId") Optional<Long> requestTypeId,
	    @RequestParam("departmentId") Optional<Long> departmentId,
	    @RequestParam("assigneeId") Optional<Long> assigneeId,
		@RequestParam("companyNb") Optional<String> companyNb) {
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
	    if (!(citizenId.isPresent() || companyNb.isPresent()) || (citizenId.isPresent() && companyNb.isPresent()))
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    if(citizenId.isPresent()){
	    	data = requestTypeId.isPresent() ? requestService.find(citizenId.get(), requestTypeId.get())
					: requestService.findByCitizenId(citizenId.get());
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}
		if(companyNb.isPresent()){
	    	data = requestTypeId.isPresent() ? requestService.findByCompanyNb(companyNb.get(), requestTypeId.get())
					: requestService.findByCompanyNb(companyNb.get());
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PostMapping(params = "requestType=" + RequestService.TYPE_PARKING_CARD, consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createRequest(@RequestPart("request") @NotNull @Valid Request newRequest,
	    @RequestPart("citizenParkingCardGreenCard") @Valid @NotNull @NotBlank MultipartFile greenCard) {
	try {
	    Map<String, MultipartFile> files = new HashMap<>();
	    files.put("citizenParkingCardGreenCard", greenCard);
	    newRequest.setTypeDescription(RequestService.TYPE_PARKING_CARD);
	    long requestId = requestService.create(newRequest, files);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(requestId)
		    .toUri();
	    return ResponseEntity.created(location).build();
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PostMapping(params = "requestType=" + RequestService.TYPE_NATIONALITY_CERTIFICATE, consumes = {
	    "multipart/form-data" })
    public ResponseEntity<Void> createRequest(@RequestPart("request") @Valid Request newRequest) {
	System.out.println(newRequest);
	try {
	    newRequest.setTypeDescription(RequestService.TYPE_NATIONALITY_CERTIFICATE);
	    long requestId = requestService.create(newRequest, new HashMap<>());
	    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(requestId)
		    .toUri();
	    return ResponseEntity.created(location).build();
	} catch (Exception ex) {
	    logger.error("Bad request", ex);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
    }

    @PatchMapping
    public ResponseEntity<Object> updateAccount(@RequestBody Request updatedRequest) {
	try {
	    requestService.update(updatedRequest);
	    return ResponseEntity.ok().build();
	} catch (BusinessException be) {
	    return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
    }
}
