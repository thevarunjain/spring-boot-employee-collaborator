package edu.sjsu.cmpe275.web.exception;

public class ConstraintViolationException extends Exception{
    public static class EmployeeNotFoundException extends RuntimeException {
        EmployeeNotFoundException(Long id) {
            super("Could not find employee " + id);
        }
    }

    public static class EmployerNotFoundException extends RuntimeException {
        EmployerNotFoundException(Long id) {
            super("Could not find employer " + id);
        }
    }
}
