package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

import be.unamur.hermes.business.service.RequestService;
import be.unamur.hermes.common.constants.RequestTypes;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;

@RestController
@RequestMapping({ "/requests" })
public class RequestController implements RequestTypes {

    private static Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
	this.requestService = requestService;
    }

    @PostAuthorize("hasPermission(returnObject,'any')")
    @GetMapping(path = "/{requestId}")
    public ResponseEntity<Request> getRequest(@PathVariable(value = "requestId") long requestId) {
	return ResponseEntity.status(HttpStatus.OK).body(requestService.find(requestId));
    }

    @PostAuthorize("hasPermission(returnObject,'any')")
    @GetMapping
    public ResponseEntity<List<Request>> getRequests(@RequestParam("citizenId") Optional<Long> citizenId,
	    @RequestParam("requestTypeId") Optional<Long> requestTypeId,
	    @RequestParam("departmentId") Optional<Long> departmentId,
	    @RequestParam("assigneeId") Optional<Long> assigneeId,
	    @RequestParam("companyNb") Optional<String> companyNb) {
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
	if (citizenId.isPresent()) {
	    data = requestTypeId.isPresent() ? requestService.find(citizenId.get(), requestTypeId.get())
		    : requestService.findByCitizenId(citizenId.get());
	    return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	if (companyNb.isPresent()) {
	    data = requestTypeId.isPresent() ? requestService.findByCompanyNb(companyNb.get(), requestTypeId.get())
		    : requestService.findByCompanyNb(companyNb.get());
	    return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping(path = "/{requestId}/file")
    public ResponseEntity<ByteArrayResource> getRequestFile(@PathVariable(value = "requestId") long requestId,
	    @RequestParam("code") String code) {
	RequestField field = requestService.findRequestFieldByCode(requestId, code);
	ByteArrayResource resource = new ByteArrayResource(field.getFieldFile());
	MediaType mediaType = MediaType.valueOf(field.getFieldFileType());

	return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).contentLength(field.getFieldFile().length)
		.body(resource);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OFFICER') or #newRequest.citizen.id == principal.technicalId")
    @PostMapping(params = "requestType=" + CITIZEN_PARKING_CARD, consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createRequest(@RequestPart("request") @Valid Request newRequest,
	    @RequestPart("citizenParkingCardGreenCard") @Valid MultipartFile greenCard,
	    @RequestPart("citizenParkingCardUserProof") Optional<MultipartFile> userProof) {
	List<MultipartFile> files = new ArrayList<>();
	files.add(greenCard);
	if (userProof.isPresent())
	    files.add(userProof.get());
	URI location = createRequest(CITIZEN_PARKING_CARD, newRequest, files);
	return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OFFICER') or #newRequest.citizen.id == principal.technicalId")
    @PostMapping(params = "requestType=" + COMPANY_PARKING_CARD, consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createRequest(@RequestPart("request") @Valid Request newRequest,
	    @RequestPart("companyParkingCardGreenCard") @Valid MultipartFile greenCard,
	    @RequestPart("companyParkingCardUserProof") MultipartFile userProof) {
	List<MultipartFile> files = Arrays.asList(greenCard, userProof);
	URI location = createRequest(COMPANY_PARKING_CARD, newRequest, files);
	return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OFFICER') or #newRequest.citizen.id == principal.technicalId")
    @PostMapping(params = "requestType=" + NATIONALITY_CERTIFICATE, consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createRequest(@RequestPart("request") @Valid Request newRequest) {
	URI location = createRequest(NATIONALITY_CERTIFICATE, newRequest, Collections.emptyList());
	return ResponseEntity.created(location).build();
    }

    @PostAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OFFICER') or returnObject.citizen.id == principal.technicalId")
    @PatchMapping
    public ResponseEntity<Request> updateRequest(@RequestBody Request updatedRequest) {
	Request result = requestService.update(updatedRequest);
	return ResponseEntity.ok(result);
    }

    private URI createRequest(String requestType, Request newRequest, List<MultipartFile> files) {
	Map<String, MultipartFile> filesMap = files.stream()
		.collect(Collectors.toMap(MultipartFile::getName, Function.identity()));
	newRequest.setTypeDescription(requestType);
	long requestId = requestService.create(newRequest, filesMap);
	return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(requestId).toUri();
    }
}
