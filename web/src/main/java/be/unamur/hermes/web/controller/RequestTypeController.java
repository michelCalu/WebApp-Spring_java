package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.MunicipalityService;
import be.unamur.hermes.business.service.RequestTypeService;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/request-types"})
public class RequestTypeController {

    private final RequestTypeService requestTypeService;
    private final MunicipalityService municipalityService;

    @Autowired
    RequestTypeController(RequestTypeService requestTypeService, MunicipalityService municipalityService){
        this.requestTypeService = requestTypeService;
        this.municipalityService = municipalityService;
    }

    @GetMapping
    public ResponseEntity<List<RequestType>> showRequestTypeByMunicipality(
            @RequestParam("municipalityId") Optional<Long> municipalityId,
            @RequestParam("municipalityName") Optional<String> municipalityName) {
        if(municipalityId.isPresent()){
            return new ResponseEntity<>(requestTypeService.findByMunicipalityId(municipalityId.get()), HttpStatus.OK);
        }
        if(municipalityName.isPresent()){
            Municipality municipality = municipalityService.findByName(municipalityName.get());
            return new ResponseEntity<>(requestTypeService.findByMunicipalityId(municipality.getId()), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
