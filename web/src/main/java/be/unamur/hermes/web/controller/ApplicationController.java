package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.InhabitantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping({"/"})
public class ApplicationController {

    private InhabitantService inhabitantService;

    @Autowired
    public ApplicationController(InhabitantService inhabitantService){
        this.inhabitantService = inhabitantService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public String serveHomePage() {
        return "index";
    }

    @GetMapping(path = "/about")
    @CrossOrigin(origins = "http://localhost:4200")
    public String serveAboutPage() {
        System.out.println("About bitches");
        inhabitantService.register("Marco", "Polo");
        return "about";
    }

}
