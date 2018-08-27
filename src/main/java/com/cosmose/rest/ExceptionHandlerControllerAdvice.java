package com.cosmose.rest;

import com.cosmose.dto.ErrorDTO;
import com.cosmose.exception.DomainEntityNotFoundException;
import com.cosmose.exception.LogicValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by damian on 25.08.18.
 */

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {DomainEntityNotFoundException.class})
    public ErrorDTO errorDTO(DomainEntityNotFoundException e) {
        return new ErrorDTO(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = {LogicValidationException.class})
    public ErrorDTO errorDTO(LogicValidationException e) {
        return new ErrorDTO(e.getMessage());
    }
}
