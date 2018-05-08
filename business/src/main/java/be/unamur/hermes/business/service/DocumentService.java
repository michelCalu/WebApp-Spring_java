package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Request;

import java.io.InputStream;
import java.util.List;

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

    long createNationalityCertificate(boolean positive, Request request, long documentTitle);

    long createParkingCardDecision(boolean positive, Request request, long documentTitle);

    long createParkingCard(Request request, long documentTitle);

    long createPayment(Request request, long documentTitle);

    InputStream findDocumentById(long documentId);

    List<Long> findDocumentByRequest(long requestId);

}
