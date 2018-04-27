package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.CitizenAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitizenAccountController {

    private final CitizenAccountService citizenAccountService;

    @Autowired
    public CitizenAccountController(CitizenAccountService citizenAccountService) {
        this.citizenAccountService = citizenAccountService;
    }
}
