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
                        // TODO Null pointer exception
//                        .zip(
//                            Objects.nonNull(params.get("zip"))
//                            ? Integer.parseInt(params.get("zip"))
//                                    : null
//                        )
                        .build())
                .build();
    }

    public EmployeeDto map(final Employee employee) {
        // TODO How to build reports (List)
/*        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .title(employee.getTitle())
                .manager(
                        Objects.nonNull(employee.getManager())
                                ? AssociatedEmployeeDetailsDto.builder()
                                .id(employee.getManager().getId())
                                .name(employee.getManager().getName())
                                .title(employee.getManager().getTitle())
                                .build()
                                : null
                )
                .reports(
                        Objects.nonNull(employee.getReports())
                                ?
                )
                .build();
        */
        // TODO What if we have null values in employee object
        Employee manager = employee.getManager();
        List<Employee> reports = employee.getReports();
        AssociatedEmployeeDetailsDto managerDto;
        List<AssociatedEmployeeDetailsDto> reportsDto;
        EmployeeDto employeeResponseDto = EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .title(employee.getTitle())
                .address(
                        AddressDto.builder()
                                .street(employee.getAddress().getStreet())
                                .city(employee.getAddress().getCity())
                                .state(employee.getAddress().getState())
                                // TODO Build zip
                                .build()
                )
                .build();
        if (manager != null) {
            managerDto = AssociatedEmployeeDetailsDto.builder()
                    .id(manager.getId())
                    .name(manager.getName())
                    .title(manager.getTitle())
                    .build();
            employeeResponseDto.setManager(managerDto);
        }
        if (reports != null) {
            reportsDto = new ArrayList<>();
            for (Employee report : reports) {
                reportsDto.add(
                        AssociatedEmployeeDetailsDto.builder()
                                .id(report.getId())
                                .name(report.getName())
                                .title(report.getTitle())
                                .build()
                );
            }
            employeeResponseDto.setReports(reportsDto);
        }
        return employeeResponseDto;
    }
}
