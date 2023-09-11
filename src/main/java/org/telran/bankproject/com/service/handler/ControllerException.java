package org.telran.bankproject.com.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telran.bankproject.com.exceptions.NotEnoughFieldsAreFilledException;
import org.telran.bankproject.com.exceptions.NotEnoughMoneyException;
import org.telran.bankproject.com.exceptions.NotRemovedDependenciesException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler
    public ResponseEntity<String> entityNotFoundException(
            EntityNotFoundException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> notEnoughFieldsAreFilledException(
            NotEnoughFieldsAreFilledException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> unsupportedOperationException(
            UnsupportedOperationException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> notEnoughMoneyException(
            NotEnoughMoneyException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> notRemovedDependenciesException(
            NotRemovedDependenciesException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY);
    }
}