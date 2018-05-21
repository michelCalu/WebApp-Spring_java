package be.unamur.hermes.web.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import be.unamur.hermes.common.exception.Error;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.common.exception.HermesException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements Errors {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Error> handle(EmptyResultDataAccessException ex) {
	logger.error(ex.getMessage(), ex);
	Error error = new Error(FAILURE_DATABASE_RETRIEVAL, ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Error> handle(SQLException ex) {
	logger.error(ex.getMessage(), ex);
	Error error = new Error(FAILURE_DATABASE_RETRIEVAL, ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HermesException.class)
    public ResponseEntity<Error> handle(HermesException ex) {
	logger.error(ex.getMessage(), ex);
	Error error = new Error(ex.getCode(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
	logger.error(ex.getMessage(), ex);
	Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}