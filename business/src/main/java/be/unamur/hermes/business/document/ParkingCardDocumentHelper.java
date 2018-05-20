package be.unamur.hermes.business.document;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import be.unamur.hermes.common.constants.Parameters;
import be.unamur.hermes.common.constants.RequestFields;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestParameters;

public class ParkingCardDocumentHelper extends DocumentHelper {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);

    public ParkingCardDocumentHelper(RequestParameters params, Request request) {
	super(params, request);
    }

    public String getPlateNumber() {
	if (isCorporateRequest())
	    return getRequestField(RequestFields.COMPANY_PLATENUMBER);
	return getRequestField(RequestFields.CITIZEN_PLATENUMBER);
    }

    public String getValidityEnd() {
	LocalDate date = getDocumentDate();
	int weeks = Integer.parseInt(params.getParameter(Parameters.PARKING_CARD_PERIOD_VALIDITY));
	return format(date.plusWeeks(weeks));
    }

    public String getValidityBegin() {
	return format(getDocumentDate());
    }

    public String getPaymentTerm() {
	LocalDate docDate = getDocumentDate();
	int paymentTerm = Integer.parseInt(params.getParameter(Parameters.PARKING_CARD_TERM_PAYMENT));
	return format(docDate.plusWeeks(paymentTerm));
    }

    public String getFee() {
	String fee = params.getParameter(Parameters.PARKING_CARD_FEE);
	return currencyFormat.format(Double.valueOf(fee));
    }

}
