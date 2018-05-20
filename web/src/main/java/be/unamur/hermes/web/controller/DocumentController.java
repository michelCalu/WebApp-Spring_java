package be.unamur.hermes.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.service.DocumentService;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.entity.Document;

@RestController
@RequestMapping({ "/documents" })
public class DocumentController implements Errors {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
	this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments(@RequestParam("requestId") long requestId) {
	List<Document> ids = documentService.findDocumentByRequest(requestId);
	return ResponseEntity.ok(ids);
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
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	    throw new BusinessException(FAILURE_DOCUMENT_GENERATION, e.getMessage());
	}
    }
}
