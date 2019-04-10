package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.EmployerMapper;
import edu.sjsu.cmpe275.web.model.response.CreateEmployerResponseDto;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    @Autowired
    public EmployerController(
            EmployerRepository employerRepository,
            EmployerMapper employerMapper
    ) {
        this.employerRepository = employerRepository;
        this.employerMapper = employerMapper;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployerResponseDto createEmployer(@RequestParam Map<String, String> params) throws Exception {

        ValidatorUtil.validate(params, "name");
        ValidatorUtil.validate(params, "description");

        return employerMapper.map(
                employerRepository.save(employerMapper.map(params))
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(final ConstraintViolationException e)
    {

    }
}
