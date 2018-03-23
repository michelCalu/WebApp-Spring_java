package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.InhabitantService;
import be.unamur.hermes.business.service.PeopleBisService;
import be.unamur.hermes.dataaccess.entity.People;
import be.unamur.hermes.dataaccess.entity.PeopleBis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping({"/"})
public class ApplicationController {

    private InhabitantService inhabitantService;
    private PeopleBisService peopleBisService;

    @Autowired
    public ApplicationController(InhabitantService inhabitantService, PeopleBisService peopleBisService){
        this.inhabitantService = inhabitantService;
        this.peopleBisService = peopleBisService;
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

    @PostMapping(path = "/createpeople")
    @CrossOrigin(origins = "http://localhost:4200")
    public void createPeople(@RequestBody People people){
        inhabitantService.register(people.getFirstname(), people.getLastname());
    }

    @GetMapping(path = "/showpeople")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<PeopleBis> showPeople(){
        return peopleBisService.findAll();
    }

}
