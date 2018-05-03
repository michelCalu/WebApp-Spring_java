package be.unamur.hermes.business.service;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import be.unamur.hermes.business.document.DocumentCreationRequest;

public interface DocumentService {

    /**
     * @returns the output as an html-formatted string
     */
    String getNationalityCertificate(boolean positive, DocumentCreationRequest document);

    /**
     * Writes the html-formatted output to the writer given in parameter.
     */
    void getNationalityCertificate(boolean positive, DocumentCreationRequest document, Writer writer);

    InputStream findDocumentById(long documentId);

    List<Long> findDocumentByRequest(long requestId);

}
