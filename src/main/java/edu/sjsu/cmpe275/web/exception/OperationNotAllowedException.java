package edu.sjsu.cmpe275.web.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class OperationNotAllowedException extends RuntimeException {
    private final String ERROR_CODE = "OPERATION_NOT_ALLOWED";

    private String parameter;

    public OperationNotAllowedException(final String message, final String parameter) {
        super(message);
        this.parameter = parameter;
    }
}