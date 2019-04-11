package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.domain.exception.EmployerNotFoundException;
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
        // TODO Which type of exception this method should throw
        ValidatorUtil.validateParams(params, Arrays.asList("name", "email", "employerId"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee createdEmployee = employeeService.createEmployee(
                employeeMapper.map(params),
                params.get("managerId"),
                params.get("employerId")
        );

        return employeeMapper.map(createdEmployee);
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
        // TODO This operation does not change collaborators
        // TODO 400 for required parameters missing

        ValidatorUtil.validateParams(params, Arrays.asList("email"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee updatedEmployee = employeeService.updateEmployee(
                id,
                employeeMapper.map(params)
        );

        return employeeMapper.map(updatedEmployee);

    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto deleteEmployee(@PathVariable @NotNull long id) {
        final Employee deletedEmployee = employeeService.deleteEmployee(id);
        return employeeMapper.map(deletedEmployee);
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleException(final EmployerNotFoundException e) {
        return new ErrorResponseDto(
                e.getERROR_CODE(),
                e.getMessage(),
                e.getId().toString()
        );
    }
}