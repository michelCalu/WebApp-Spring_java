package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;

public class MunicipalityController {

    private final MunicipalityService municipalityService;


    @Autowired
    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }
}
