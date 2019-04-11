package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class EmployerService {
    private EmployerRepository employerRepository;

    @Autowired
    public EmployerService(final EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Transactional
    public Employer createEmployer(final Employer employer) {

        return employerRepository.save(employer);
    }


}
