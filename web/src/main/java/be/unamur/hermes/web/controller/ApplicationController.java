package be.unamur.hermes.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.parameters.RequestType;
import be.unamur.hermes.business.service.ParameterService;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private ParameterService parameterService;

    @Autowired
    public ApplicationController(ParameterService parameterService) {
	this.parameterService = parameterService;
    }

    @GetMapping
    public String serveHomePage() {
	return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
	return "about";
    }

    @GetMapping(path = "/parameters/{municipality}")
    public ResponseEntity<RequestType> getClaimType(@PathVariable(value = "municipality") String municipality,
	    @RequestParam("requestTypeId") String requestTypeId) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getRequestType(municipality, requestTypeId));
    }

    @GetMapping(path = "/parameters/{municipality}/all")
    public ResponseEntity<List<RequestType>> getClaimTypes(@PathVariable(value = "municipality") String municipality) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getRequestTypes(municipality));
    }

    @GetMapping(path = "/parameters/{municipality}/{requestTypeId}")
    public ResponseEntity<String> getParameter(@PathVariable(value = "municipality") String municipality,
	    @PathVariable(value = "requestTypeId") String requestTypeId,
	    @RequestParam("parameterId") String parameterId) {
	// e.g. to know if a type of request is activated :
	// "/parameters/Gembloux/perm-parking?parameterId=activated"
	return ResponseEntity.status(HttpStatus.OK)
		.body(parameterService.getParameter(municipality, requestTypeId, parameterId));
    }
}
