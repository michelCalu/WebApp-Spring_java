package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.InhabitantService;
import be.unamur.hermes.business.service.PeopleBisService;
import be.unamur.hermes.dataaccess.entity.PeopleBis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public String serveHomePage() {
        return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
        System.out.println("About bitches");
        inhabitantService.register("Marco", "Polo");
        return "about";
    }

    @PostMapping(path = "/createpeople")
    public ResponseEntity<Void> createPeople(@RequestBody String peopleBis){
        System.out.println(peopleBis);
        inhabitantService.register("oscaR", "CBLS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showpeople")
    public ResponseEntity<List<PeopleBis>> showPeople(){
        return ResponseEntity.status(HttpStatus.OK).body(peopleBisService.findAll());
    }

}
