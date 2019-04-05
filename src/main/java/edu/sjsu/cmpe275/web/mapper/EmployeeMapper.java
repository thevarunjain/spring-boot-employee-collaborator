package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.web.model.response.CreateEmployeeResponseDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeMapper {
    public Employee map(final Map<String, String> params) {
        return Employee.builder()
                .name(params.get("name"))
                .email(params.get("email"))
                .build();
    }

    public CreateEmployeeResponseDto map(final Employee employee) {
        return CreateEmployeeResponseDto.builder()
                .id(employee.getId())
                .build();
    }
}
