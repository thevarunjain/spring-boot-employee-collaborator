package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Employer;
import org.springframework.stereotype.Component;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;

import java.util.Map;

@Component
public class EmployerMapper {
    public Employer map(final Map<String, String> params) {
        return Employer.builder()
                .name(params.get("name"))
                .description(params.get("description"))
                .build();
    }
    public EmployerDto map(final Employer employer) {
        return EmployerDto.builder()
                .id(employer.getId())
                .name(employer.getName())
                .description(employer.getDescription())
                .build();
    }
}
