package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.web.model.response.CreateEmployerResponseDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployerMapper {
    public Employer map(final Map<String, String> params) {
        return Employer.builder()
                .name(params.get("name"))
                .description(params.get("description"))
                .build();
    }
    public CreateEmployerResponseDto map(final Employer employer) {
        return CreateEmployerResponseDto.builder()
                .id(employer.getId())
                .build();
    }
}
