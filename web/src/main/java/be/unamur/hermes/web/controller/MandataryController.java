package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.MandataryService;
import org.springframework.beans.factory.annotation.Autowired;

public class MandataryController {

    private final MandataryService mandataryService;

    @Autowired
    public MandataryController(MandataryService mandataryService) {
        this.mandataryService = mandataryService;
    }
}
