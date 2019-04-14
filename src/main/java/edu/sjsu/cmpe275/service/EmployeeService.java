package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.exception.OperationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployerService employerService;

    @Autowired
    public EmployeeService(
            final EmployeeRepository employeeRepository,
            final EmployerService employerService
    ) {
        this.employeeRepository = employeeRepository;
        this.employerService = employerService;
    }

    @Transactional
    public Employee createEmployee(
            final Employee toCreate,
            final String managerId,
            final String employerId
    ) {
        Employee newEmployee = setManagerAndEmployer(toCreate, managerId, employerId);
        // TODO Duplicate email address exception / Currently empty body
        return employeeRepository.save(newEmployee);
    }

    public Employee findEmployee(final Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public Employee updateEmployee(
            final Long id,
            final Employee fromRequest,
            final String managerId,
            final String employerId
    ) {
        Employee existingEmployee = findEmployee(id);
        existingEmployee = setManagerAndEmployer(existingEmployee, managerId, employerId);
        existingEmployee.update(fromRequest);
        return existingEmployee;
    }

    @Transactional
    public Employee deleteEmployee(final Long id) {
        // TODO Remove any references related to collaboration
        final Employee toDelete = findEmployee(id);
        // TODO Instead of checking reports directly delete employee
        // and let database handle it similar to employer
        if (toDelete.getReports().size() > 0) {
            // TODO Benefit of extending to ConstraintViolation
            throw new OperationNotAllowedException("Employee still has reports", id.toString());
        }
        employeeRepository.delete(toDelete);
        return toDelete;
    }

    private Employee setManagerAndEmployer(Employee employee, String managerId, String employerId) {
        // TODO Circular manager loop
        if ( Objects.nonNull(employee.getEmployer()) &&
                !Long.valueOf(employerId).equals(employee.getEmployer().getId())) {
            // Employee is changing employer
            if (Objects.nonNull(employee.getManager())) {
                // TODO Not getting reflected in PUT properly
                for (Employee report: employee.getReports()) {
                    report.setManager(employee.getManager());
                }
            } else {
                for (Employee report: employee.getReports()) {
                    report.setManager(null);
                }
            }
        }
        // May be changing manager under same employer  New employee creation OR
        if (Objects.nonNull(managerId)) {
            // TODO No Need to introduce new exception, EmployeeNotFound would suffice
            final Employee manager = findEmployee(Long.valueOf(managerId));
            // TODO Optimize this
            if (Long.valueOf(employerId).equals(manager.getEmployer().getId())) {
                employee.setManager(manager);
            } else {
                throw new OperationNotAllowedException("Manager should be from same Employer", employerId);
            }
        }
        final Employer employer = employerService.findEmployer(Long.valueOf(employerId));
        employee.setEmployer(employer);
        return employee;
    }
}