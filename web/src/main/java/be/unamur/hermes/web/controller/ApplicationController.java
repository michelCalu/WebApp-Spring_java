package be.unamur.hermes.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    @GetMapping
    public String serveHomePage() {
	return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
	return "about";
    }
}
