package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.RequestTypeService;
import be.unamur.hermes.dataaccess.entity.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/request-types"})
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    @Autowired
    RequestTypeController(RequestTypeService requestTypeService){
        this.requestTypeService = requestTypeService;
    }

    @GetMapping
    public ResponseEntity<List<RequestType>> showRequestTypeByMunicipality(@RequestParam("municipalityId") Long municipalityId) {
        return new ResponseEntity<>(requestTypeService.findByMunicipalityId(municipalityId), HttpStatus.OK);
    }

}
