package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Address;
import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.web.model.response.AddressDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedEmployeeDetailsDto;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
    public Employee map(final Map<String, String> params) {
        return Employee.builder()
                .name(params.get("name"))
                .email(params.get("email"))
                .title(params.get("title"))
                .address(Address.builder()
                        .street(params.get("street"))
                        .city(params.get("city"))
                        .state(params.get("state"))
                        .zip(params.get("zip"))
                        .build())
                .build();
    }

    public EmployeeDto map(final Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .title(employee.getTitle())
                .address(mapAddress(employee.getAddress()))
                .manager(mapManager(employee)) // TODO Changed from employee.getManager()
                .reports(mapReports(employee.getReports()))
                .build();
    }

    private AddressDto mapAddress(final Address address) {
        // TODO If address has all values null then entire object is null ?
        return Objects.nonNull(address)
                ? AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .build()
                : null;
    }

    private AssociatedEmployeeDetailsDto mapManager(final Employee employee) { // TODO Changed from manager
        return Objects.nonNull(employee.getManager())
                ? AssociatedEmployeeDetailsDto.builder()
                .id(employee.getManager().getId())
                .name(employee.getManager().getName())
                .title(employee.getManager().getTitle())
                .build()
                : null;
    }

    private List<AssociatedEmployeeDetailsDto> mapReports(final List<Employee> employees) {
        return Objects.nonNull(employees) // TODO Added this
                ? employees
                .stream()
                .map(report -> AssociatedEmployeeDetailsDto.builder()
                        .id(report.getId())
                        .name(report.getName())
                        .title(report.getTitle())
                        .build()
                )
                .collect(Collectors.toList())
                : new ArrayList<>(); // TODO instead of null
    }
}
