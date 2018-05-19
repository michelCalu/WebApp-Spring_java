package be.unamur.hermes.business.document;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.constants.RequestTypes;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestParameters;

public class DocumentHelperFactory {

    private Request request;
    private RequestParameters parameters;

    public DocumentHelperFactory(Request request, RequestParameters parameters) {
	super();
	this.request = request;
	this.parameters = parameters;
    }

    public DocumentHelper getDocumentHelper() {
	String typeDescription = request.getTypeDescription();
	if (RequestTypes.NATIONALITY_CERTIFICATE.equalsIgnoreCase(typeDescription))
	    return new DocumentHelper(parameters, request);
	if (RequestTypes.COMPANY_PARKING_CARD.equalsIgnoreCase(typeDescription)
		|| RequestTypes.CITIZEN_PARKING_CARD.equalsIgnoreCase(typeDescription))
	    return new ParkingCardDocumentHelper(parameters, request);
	throw new BusinessException("Unknown request type : " + typeDescription);
    }

}
