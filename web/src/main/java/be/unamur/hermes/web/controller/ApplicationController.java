package be.unamur.hermes.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() {
        return "index";
    }

    @RequestMapping(path = "/about", produces = MediaType.TEXT_HTML_VALUE)
    public String serveAboutPage() {
        return "about";
    }

}
