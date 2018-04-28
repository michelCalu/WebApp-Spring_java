package be.unamur.hermes.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.DocumentService;

@RestController
@RequestMapping({ "/pdf" })
public class DocumentController {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
	this.documentService = documentService;
    }

    public void download(HttpServletResponse response) {
	response.setContentType("application/pdf");
	try {
	    PrintWriter writer = response.getWriter();
	    // TODO
	    writer.flush();
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	}
    }
}
