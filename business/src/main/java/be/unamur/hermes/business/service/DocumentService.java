package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Document;
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

    public static final String TITLE_NATIONALITY_CERTIFICATE="nationalityCertificate";
    public static final String TITLE_PARKING_CARD_CITIZEN="citizeParkingCard";
    public static final String TITLE_PARKING_CARD_DECISION="citizenParkingDecision";
    public static final String TITLE_PARKING_CARD_PAYMENT="citizenParkingPayment";

    long createNationalityCertificate(boolean positive, Request request);

    long createParkingCardDecision(boolean positive, Request request);

    long createParkingCard(Request request);

    long createPayment(Request request);

    InputStream findDocumentById(long documentId);

    List<Document> findDocumentByRequest(long requestId);

}
