package be.unamur.hermes.business.service;

import be.unamur.hermes.business.document.DocumentCreationRequest;

public interface DocumentService {

    String getNationalityCertificate(boolean positive, DocumentCreationRequest document);

}
