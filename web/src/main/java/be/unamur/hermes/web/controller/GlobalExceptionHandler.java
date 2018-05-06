package be.unamur.hermes.web.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNNotValidException;
import be.unamur.hermes.common.exception.Error;
import be.unamur.hermes.common.exception.InvalidNRNFormatException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
	Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Error> handle(EmptyResultDataAccessException ex) {
	Error error = new Error(HttpStatus.NO_CONTENT.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Error> handle(BusinessException ex) {
	Error error = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNRNFormatException.class)
    public ResponseEntity<Error> handle(InvalidNRNFormatException ex) {
	Error error = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NRNNotValidException.class)
    public ResponseEntity<Error> handle(NRNNotValidException ex) {
	Error error = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

}