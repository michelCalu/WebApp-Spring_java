package be.unamur.hermes.web.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.unamur.hermes.business.service.ParameterService;
import be.unamur.hermes.dataaccess.entity.RequestParameters;

@RestController
@RequestMapping({ "/parameters" })
public class ParameterController {

    private ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService parameterService) {
	this.parameterService = parameterService;
    }

    // CREATE
    @PostMapping(path = "/{municipalityId}/{requestTypeId}")
    public ResponseEntity<Object> createParameters(@RequestBody Map<String, String> parameters,
	    @PathVariable(value = "municipalityId") long municipalityId,
	    @PathVariable(value = "requestTypeId") long requestTypeId) {
	parameterService.create(parameters, municipalityId, requestTypeId);
	URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
	return ResponseEntity.created(location).build();
    }

    // READ

    @GetMapping(path = "/{municipalityId}/{requestTypeId}", params = "!parameterId")
    public ResponseEntity<RequestParameters> getParameters(@PathVariable(value = "municipalityId") long municipalityId,
	    @PathVariable(value = "requestTypeId") long requestTypeId) {
	return ResponseEntity.ok(parameterService.getRequestType(municipalityId, requestTypeId));
    }

    @GetMapping(path = "/{municipalityId}/{requestTypeId}")
    public ResponseEntity<Map<String, String>> getParameter(@PathVariable(value = "municipalityId") long municipalityId,
	    @PathVariable(value = "requestTypeId") long requestTypeId,
	    @RequestParam("parameterId") String parameterId) {
	// e.g. to know if a requestType is activated :
	// GET /parameters/1/3?parameterId=activated
	String parameterValue = parameterService.getParameter(municipalityId, requestTypeId, parameterId);
	return ResponseEntity.ok(Collections.singletonMap(parameterId, parameterValue));
    }

    // UPDATE
    @PatchMapping(path = "/{municipalityId}/{requestTypeId}")
    public ResponseEntity<Object> updateParameters(@RequestBody Map<String, String> parameters,
	    @PathVariable(value = "municipalityId") long municipalityId,
	    @PathVariable(value = "requestTypeId") long requestTypeId) {
	parameterService.update(parameters, municipalityId, requestTypeId);
	return ResponseEntity.ok().build();
    }
}
