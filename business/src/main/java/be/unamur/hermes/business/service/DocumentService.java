package be.unamur.hermes.business.service;

import java.io.InputStream;
import java.util.List;

import be.unamur.hermes.dataaccess.entity.Request;

/**
 * The DocumentService has a double purpose :
 * <ul>
 * <li>It handles the generation of official documents</li>
 * <li>It serves as gateway with the DocumentRepository</li>
 * </ul>
 * 
 * @author Thomas_Elskens
 *
 */
public interface DocumentService {

    long createNationalityCertificate(boolean positive, Request request);

    long createParkingCardDecision(boolean positive, Request request);

    long createParkingCard(Request request);

    long createPayment(Request request);

    InputStream findDocumentById(long documentId);

    List<Long> findDocumentByRequest(long requestId);

}
