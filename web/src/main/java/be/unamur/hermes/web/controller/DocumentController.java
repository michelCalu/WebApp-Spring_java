package be.unamur.hermes.web.controller;

import be.unamur.hermes.business.service.DocumentService;
import be.unamur.hermes.dataaccess.entity.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping({ "/documents" })
public class DocumentController {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
	this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments(@RequestParam("requestId") long requestId) {
	try {
	    List<Document> ids = documentService.findDocumentByRequest(requestId);
	    return ResponseEntity.ok(ids);
	} catch (Exception ex) {
	    logger.error(ex.getMessage(), ex);
	    return ResponseEntity.badRequest().build();
	}
    }

    @GetMapping(path = "/{documentId}")
    public void getDocument(@PathVariable(value = "documentId") long documentId, HttpServletResponse response) {
	try (InputStream is = documentService.findDocumentById(documentId);) {
	    response.setContentType("application/pdf");
	    String documentName = "document_" + documentId + ".pdf";
	    response.setHeader("Content-Disposition",
		    "attachment; name=\"" + documentName + "\"; filename=\"" + documentName + "\"");
	    StreamUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
	    // return ResponseEntity.ok().build();
	} catch (Exception ex) {
	    logger.error("Get Document failed", ex);
	    // return ResponseEntity.badRequest().build();
	}
    }

    // public void download(HttpServletResponse response) {
    // response.setContentType("application/pdf");
    // try {
    // PrintWriter writer = response.getWriter();
    // // TODO
    // writer.flush();
    // } catch (IOException e) {
    // logger.error(e.getMessage(), e);
    // }
    // }
}
