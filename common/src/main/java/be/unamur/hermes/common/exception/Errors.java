package be.unamur.hermes.common.exception;

/**
 * List of all error codes.
 * 
 * @author Thomas_Elskens
 *
 */
public interface Errors {

    int ERROR_UNKNOWN = -1;

    // invalid data 0 -> 9999
    int INVALID_NRN = 0;
    int INVALID_FIRST_NAME = 1;
    int INVALID_LAST_NAME = 2;
    int INVALID_PHONE_NUMBER = 3;
    int INVALID_EMAIL = 4;
    int INVALID_BIRTH_DATE = 5;
    int INVALID_COUNTRY = 6;
    int INVALID_STATE = 7;
    int INVALID_ZIPCODE = 8;
    int INVALID_STREET = 9;
    int INVALID_STREET_NUMBER = 10;
    int INVALID_MUNICIPALITY = 11;
    int INVALID_COMPANY_NUMBER = 12;
    int INVALID_VAT_NUMBER = 13;
    int INVALID_LEGAL_FORM = 14;
    int INVALID_USER_STATUS = 15;
    int INVALID_XML = 16;
    int INVALID_REQUEST_FIELD = 17;
    int INVALID_FILE = 18;
    int INVALID_REQUEST_STATUS = 19;

    // missing data 10000 -> 19999
    int MISSING_NRN = 10000;
    int MISSING_REQUEST_TYPE = 10001;
    int MISSING_PASSWORD = 10002;
    int MISSING_MUNICIPALITY = 10011;
    int MISSING_REQUEST_FIELD = 10017;

    // operation failed 20000 -> 29999
    int FAILURE_DOCUMENT_GENERATION = 20000;
    int FAILURE_DATABASE_RETRIEVAL = 20001;
    int FAILURE_AUTHENTICATION = 20002;
    int FAILURE_CONNEXION_NRN_SERVICE = 20003;
}
