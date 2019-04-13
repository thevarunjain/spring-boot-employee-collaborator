package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Address;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.web.model.response.AddressDto;
import org.springframework.stereotype.Component;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;

import java.util.Map;

@Component
public class EmployerMapper {
    public Employer map(final Map<String, String> params) {
        return Employer.builder()
                .name(params.get("name"))
                .description(params.get("description"))
                .address(Address.builder()
                        .street(params.get("street"))
                        .city(params.get("city"))
                        .state(params.get("state"))
                        .zip(params.get("zip"))
                        .build())
                .build();
    }

    public EmployerDto map(final Employer employer) {
        return EmployerDto.builder()
                .id(employer.getId())
                .name(employer.getName())
                .description(employer.getDescription())
                .address(mapAddress(employer.getAddress()))
                .build();
    }

    private AddressDto mapAddress(final Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .state(address.getState())
                .street(address.getStreet())
                .zip(address.getZip())
                .build();
    }
}
