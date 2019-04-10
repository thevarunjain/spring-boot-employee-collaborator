package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee createEmployee(final Employee employee, final String managerId) {

        if (Objects.nonNull(managerId)) {
            final Employee manager = employeeRepository.findById(Long.valueOf(managerId))
                    .orElseThrow(ConstraintViolationException::new);
            employee.setManager(manager);
        }

        return employeeRepository.save(employee);
    }

    public Employee getEmployee(final Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public Employee updateEmployee(final Long id, final Employee employee) {

        final Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(ConstraintViolationException::new);

        return existingEmployee.update(employee);
    }
}
