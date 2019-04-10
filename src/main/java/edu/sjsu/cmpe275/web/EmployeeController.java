package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.service.EmployeeService;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import edu.sjsu.cmpe275.web.model.response.ErrorResponseDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@RequestParam Map<String, String> params)
            throws ConstraintViolationException {
        // TODO Employer entity must exist
        ValidatorUtil.validateParams(params, Arrays.asList("name", "email", "employerId"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee employee = employeeService.createEmployee(
                employeeMapper.map(params),
                params.get("managerId")
        );

        return employeeMapper.map(employee);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployee(@PathVariable @NotNull Long id) {
        // TODO For each collaborator, include only the id, name, title, and employer with id and name
        return employeeMapper.map(employeeService.getEmployee(id));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@PathVariable @NotNull Long id, @RequestParam Map<String, String> params)
            throws ConstraintViolationException {
        // TODO All employee fields except collaborators should be passed in as query parameters
        // TODO This operation does not change collaborators
        // TODO Changing employees employer is allowed
        // TODO managerId should be from same employer
        // TODO If employee not found then 404
        // TODO 400 for required parameters missing
        // TODO Actually update existing employee

        ValidatorUtil.validateParam(params, "email");
       /* Employee toUpdate = employeeRepository.getOne(id);
        return employeeMapper.map(
                employeeRepository.save(employeeMapper.map(params))
        );*/

        return null;

    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto deleteEmployee(@PathVariable long id) {
        // TODO If employee not found then 404
        // TODO if employee has reports then 400
        /*Employee toDelete = employeeRepository.getOne(id); // TODO GetOne can throw exception
        if (toDelete.getReports().size() > 0) {
            System.out.println("Employee has reports");
        }
        employeeRepository.deleteById(id);
        return employeeMapper.map(toDelete);*/

        return null;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto handleException(final ConstraintViolationException e) {
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getParameter()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(final RuntimeException e) {
        if (e instanceof DataIntegrityViolationException
                || e instanceof javax.validation.ConstraintViolationException
        ) {
            return;
        }

        throw e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleException(final EmployeeNotFoundException e) {
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getId().toString()
        );
    }
}
