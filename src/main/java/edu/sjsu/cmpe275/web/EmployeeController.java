package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.service.EmployeeService;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public EmployeeDto createEmployee(@RequestParam Map<String, String> params) {
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
        return employeeMapper.map(employeeService.findEmployee(id));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@PathVariable @NotNull Long id, @RequestParam Map<String, String> params) {
        ValidatorUtil.validateParams(params, Arrays.asList("email", "employerId"));
        ValidatorUtil.validateRestrictedParam(params, Arrays.asList("collaborators", "reports"));

        final Employee updatedEmployee = employeeService.updateEmployee(
                id,
                employeeMapper.map(params),
                params.get("managerId"),
                params.get("employerId")
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
}