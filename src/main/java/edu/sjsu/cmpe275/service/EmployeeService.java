package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    // TODO Is this valid - (This was required to verify if the employer entity exist while employee creation)
    private EmployerRepository employerRepository;

    @Autowired
    public EmployeeService(
            final EmployeeRepository employeeRepository,
            final EmployerRepository employerRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
    }

    @Transactional
    public Employee createEmployee(final Employee toCreate, final String managerId, final String employerId) {
        // TODO Employer entity must exist
        if (Objects.nonNull(managerId)) {
            final Employee manager = employeeRepository.findById(Long.valueOf(managerId))
                    .orElseThrow(ConstraintViolationException::new);
            toCreate.setManager(manager);
        }
        final Employer employer = employerRepository.findById(Long.valueOf(employerId))
                .orElseThrow(() -> new EmployerNotFoundException(Long.valueOf(employerId)));
        toCreate.setEmployer(employer);
        return employeeRepository.save(toCreate);
    }

    public Employee getEmployee(final Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public Employee updateEmployee(final Long id, final Employee fromRequest) {
        // TODO Why constraint violation doesn't work here
        // TODO Changing employees employer is allowed
        // TODO managerId should be from same employer
        // TODO Need to implement
        final Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return existingEmployee.update(fromRequest);
    }

    @Transactional
    public Employee deleteEmployee(final Long id) {
        // TODO Remove any references related to collaboration
        final Employee toDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        if (toDelete.getReports().size() > 0) {
            throw new ConstraintViolationException(); //TODO Proper Message
        }
        return toDelete;
    }
}
