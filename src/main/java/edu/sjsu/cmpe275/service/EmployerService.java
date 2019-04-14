package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

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

    public Employer findEmployer(final Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException(id));
    }

    @Transactional
    public Employer updateEmployer(final Long id, final Employer fromRequest) {
        final Employer existingEmployer = findEmployer(id);
        existingEmployer.update(fromRequest);
        return existingEmployer;
    }

    @Transactional
    public Employer deleteEmployer(final Long id) {
        final Employer toDelete = findEmployer(id);
        employerRepository.delete(toDelete);
        return toDelete;
    }
}
