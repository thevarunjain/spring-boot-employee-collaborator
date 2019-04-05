package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import edu.sjsu.cmpe275.web.model.response.CreateEmployeeResponseDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployeeResponseDto createEmployee(@RequestParam Map<String, String> params) throws Exception {

        ValidatorUtil.validate(params, "name");
        ValidatorUtil.validate(params, "email");

        return employeeMapper.map(
                employeeRepository.save(employeeMapper.map(params))
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(final ConstraintViolationException e)
    {

    }
}
