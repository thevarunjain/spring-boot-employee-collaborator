package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.service.EmployerService;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.EmployerMapper;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;


import java.util.Map;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private final EmployerService employerService;
    private final EmployerMapper employerMapper;

    @Autowired
    public EmployerController(
            EmployerService employerService,
            EmployerMapper employerMapper
    ) {
        this.employerService = employerService;
        this.employerMapper = employerMapper;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public EmployerDto createEmployer(@RequestParam Map<String, String> params) throws ConstraintViolationException {

        ValidatorUtil.validateParam(params, "name");
//        ValidatorUtil.validateParam(params, "description");


        final Employer employer = employerService.createEmployer(employerMapper.map(params));


        return employerMapper.map(employer);

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(final ConstraintViolationException e)
    {

    }
}
