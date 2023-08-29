package org.telran.bankproject.com.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerException {

//    @ExceptionHandler
//    public ResponseEntity entityNotFoundException(EntityNotFoundException exception,
//                                                  HttpServletRequest request) {
//        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
//    }
}