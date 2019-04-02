package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(
            final EmployeeRepository employeeRepository,
            final EmployeeMapper employeeMapper
    )
    {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }
}
