package edu.sjsu.cmpe275.web.exception;
import edu.sjsu.cmpe275.domain.entity.Employer;

public class ConstraintViolationException extends Exception{
    public static class EmployeeNotFoundException extends RuntimeException {
        EmployeeNotFoundException(Long id) {
            super("Could not find employee " + id);
        }
    }

    public static class EmployerNotFoundException extends RuntimeException {
        EmployerNotFoundException(Employer employer) {
            super("Could not find employer " + employer.getId());
        }
    }
}
