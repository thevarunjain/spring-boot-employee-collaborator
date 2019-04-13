package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.exception.OperationNotAllowedException;
import edu.sjsu.cmpe275.web.model.response.ErrorResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto handleException(final ConstraintViolationException e) {
        // TODO Need to combine ConstraintViolationException and OperationNotAllowedException handler ?
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getParameter()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto handleException(final OperationNotAllowedException e) {
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getParameter()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleException(final EmployerNotFoundException e) {
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getId().toString()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(final RuntimeException e) {
        if (e instanceof DataIntegrityViolationException
                || e instanceof javax.validation.ConstraintViolationException
        ) {
            // TODO Should we send proper body
            // TODO How do we end up determining these types
            return;
        }
        throw e;
    }
}
