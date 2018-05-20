package be.unamur.hermes.business.exception.NRNNotValidExceptions;

import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.common.exception.HermesException;

public class NRNServiceAccessException extends HermesException {

    public NRNServiceAccessException(String msg) {
	super(Errors.FAILURE_CONNEXION_NRN_SERVICE, msg);
    }

}
