package be.unamur.hermes.common.exception;

public class NRNNotValidException extends HermesException {

    public NRNNotValidException(String msg) {
	super(Errors.INVALID_NRN, msg);
    }

}
