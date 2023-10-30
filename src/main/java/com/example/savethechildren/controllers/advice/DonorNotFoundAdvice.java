package com.example.savethechildren.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.savethechildren.exceptions.DonorNotFoundException;

/**
 * Attaches itself to the Controller and handles the relevant exceptions.
 */

@ControllerAdvice
public class DonorNotFoundAdvice {

	
  @ResponseBody
  @ExceptionHandler(DonorNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(DonorNotFoundException ex) {
    return ex.getMessage();
  }

}
