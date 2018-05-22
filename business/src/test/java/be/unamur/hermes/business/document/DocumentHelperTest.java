package be.unamur.hermes.business.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.Test;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.constants.Parameters;
import be.unamur.hermes.common.constants.RequestFields;
import be.unamur.hermes.common.constants.RequestTypes;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.entity.RequestParameters;
import be.unamur.hermes.dataaccess.entity.User;

public class DocumentHelperTest {

    @Test
    public void test_documentHelperFactory_nationalityCertificate() {
	Request request = new Request(1L);
	request.setTypeDescription(RequestTypes.NATIONALITY_CERTIFICATE);
	DocumentHelperFactory classUnderTest = new DocumentHelperFactory(request, null);
	DocumentHelper result = classUnderTest.getDocumentHelper();
	assertTrue(result.getClass() == DocumentHelper.class);
    }

    @Test
    public void test_documenthelperfactory_citizenParkingCard() {
	Request request = new Request(1L);
	request.setTypeDescription(RequestTypes.CITIZEN_PARKING_CARD);
	DocumentHelperFactory classUnderTest = new DocumentHelperFactory(request, null);
	DocumentHelper result = classUnderTest.getDocumentHelper();
	assertTrue(result.getClass() == ParkingCardDocumentHelper.class);
    }

    @Test
    public void test_documenthelperfactory_companyParkingCard() {
	Request request = new Request(1L);
	request.setTypeDescription(RequestTypes.COMPANY_PARKING_CARD);
	DocumentHelperFactory helper = new DocumentHelperFactory(request, null);
	DocumentHelper result = helper.getDocumentHelper();
	assertTrue(result.getClass() == ParkingCardDocumentHelper.class);
    }

    @Test(expected = BusinessException.class)
    public void test_documenthelperfactory_unknown() {
	Request request = new Request(1L);
	request.setTypeDescription("INVALID_TYPE_DESCRIPTION");
	DocumentHelperFactory helper = new DocumentHelperFactory(request, null);
	helper.getDocumentHelper();
    }

    @Test
    public void test_documentHelper_formatDate() {
	DocumentHelper helper = new DocumentHelper(null, null);
	String formattedDate = helper.format(LocalDate.of(1985, 2, 13));
	assertEquals("13/02/1985", formattedDate);
    }

    @Test
    public void test_documentHelper_formatName() {
	DocumentHelper helper = new DocumentHelper(null, null);
	User user = new Citizen();
	user.setFirstName("Thomas");
	user.setLastName("Elskens");
	assertEquals("Elskens Thomas", helper.formatName(user));
    }

    @Test
    public void test_parkingCardDocumentHelper_validityEnd() {
	int weeks = 5;
	RequestParameters params = new RequestParameters(1L, 1L,
		Collections.singletonMap(Parameters.PARKING_CARD_PERIOD_VALIDITY, String.valueOf(weeks)));
	final LocalDate documentDate = LocalDate.of(2018, 11, 5);
	final LocalDate expectedEndDate = documentDate.plusDays(weeks * 7);
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(params, new Request(1L)) {
	    @Override
	    protected LocalDate getDocumentDate() {
		return documentDate;
	    }
	};
	String formattedEndDate = helper.getValidityEnd();
	assertEquals(expectedEndDate, LocalDate.parse(formattedEndDate, DocumentHelper.dtf));
    }

    @Test
    public void test_parkingCardDocumentHelper_validityBegin() {
	int weeks = 1;
	RequestParameters params = new RequestParameters(1L, 1L,
		Collections.singletonMap(Parameters.PARKING_CARD_PERIOD_VALIDITY, String.valueOf(weeks)));
	final LocalDate expectedBeginDate = LocalDate.now();
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(params, new Request(1L));
	String formattedBeginDate = helper.getValidityBegin();
	assertEquals(expectedBeginDate, LocalDate.parse(formattedBeginDate, DocumentHelper.dtf));
    }

    @Test
    public void test_parkingCardDocumentHelper_paymentTerm() {
	int weeks = 15;
	RequestParameters params = new RequestParameters(1L, 1L,
		Collections.singletonMap(Parameters.PARKING_CARD_TERM_PAYMENT, String.valueOf(weeks)));
	final LocalDate documentDate = LocalDate.of(2018, 5, 15);
	final LocalDate expectedPaymentTerm = documentDate.plusDays(weeks * 7);
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(params, new Request(1L)) {
	    @Override
	    protected LocalDate getDocumentDate() {
		return documentDate;
	    }
	};
	String formattedPaymentTerm = helper.getPaymentTerm();
	assertEquals(expectedPaymentTerm, LocalDate.parse(formattedPaymentTerm, DocumentHelper.dtf));
    }

    @Test
    public void test_parkingCardDocumentHelper_fee() {
	double fee = 1500.5;
	RequestParameters params = new RequestParameters(1L, 1L,
		Collections.singletonMap(Parameters.PARKING_CARD_FEE, String.valueOf(fee)));
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(params, new Request(1L));
	// the formatter uses a non breaking space, and not a normal white space
	assertEquals("1" + '\u00A0' + "500,50 €", helper.getFee());
    }

    @Test
    public void test_parkingCardDocumentHelper_isCorporateRequest() {
	Request request = new Request(1L);
	request.setCompany(new Company());
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(null, request);
	assertTrue(helper.isCorporateRequest());
    }

    @Test(expected = BusinessException.class)
    public void test_parkingCardDocumentHelper_plateNumber_mismatch() {
	Request request = new Request(1L);
	RequestField field = new RequestField();
	field.setCode(RequestFields.COMPANY_PLATENUMBER);
	field.setFieldValue("SomePlateNumber");
	request.addRequestField(field);
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(null, request);
	helper.getPlateNumber();
    }

    @Test
    public void test_parkingCardDocumentHelper_plateNumber_company() {
	Request request = new Request(1L);
	request.setCompany(new Company());
	RequestField field = new RequestField();
	field.setCode(RequestFields.COMPANY_PLATENUMBER);
	field.setFieldValue("SomePlateNumber");
	request.addRequestField(field);
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(null, request);
	assertEquals("SomePlateNumber", helper.getPlateNumber());
    }

    @Test
    public void test_parkingCardDocumentHelper_plateNumber_citizen() {
	Request request = new Request(1L);
	RequestField field = new RequestField();
	field.setCode(RequestFields.CITIZEN_PLATENUMBER);
	field.setFieldValue("SomePlateNumber");
	request.addRequestField(field);
	ParkingCardDocumentHelper helper = new ParkingCardDocumentHelper(null, request);
	assertEquals("SomePlateNumber", helper.getPlateNumber());
    }

}
