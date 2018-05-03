package be.unamur.hermes.business.service;

import java.io.InputStream;
import java.util.List;

import be.unamur.hermes.business.document.DocumentCreationRequest;

public interface DocumentService {

    /**
     * @returns the output as an html-formatted string
     */
    String getNationalityCertificate(boolean positive, DocumentCreationRequest document);

    String getParkingCardDecision(boolean positive, DocumentCreationRequest document);

    String getParkingCard(DocumentCreationRequest document);

    String getPayment(DocumentCreationRequest document);

    InputStream findDocumentById(long documentId);

    List<Long> findDocumentByRequest(long requestId);

}
