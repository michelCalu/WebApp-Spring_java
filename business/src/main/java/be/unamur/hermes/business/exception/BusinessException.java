package be.unamur.hermes.business.exception;

import be.unamur.hermes.common.exception.HermesException;

public class BusinessException extends HermesException {

    public BusinessException(int code, String message) {
	super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
	super(code, message);
	this.initCause(cause);
    }

}
