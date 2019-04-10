package edu.sjsu.cmpe275.web.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ConstraintViolationException extends RuntimeException {
    private final String ERROR_CODE = "CONSTRAINT_VIOLATION_CODE";

    private String parameter;

    public ConstraintViolationException(final String parameter) {
        super("Request failed constaint violation.");
        this.parameter = parameter;
    }
}
