package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.web.model.response.AssociatedEmployeeDetailsDto;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class EmployeeMapper {
    public Employee map(final Map<String, String> params) {
        // TODO How to build address
        return Employee.builder()
                .name(params.get("name"))
                .email(params.get("email"))
                .title(params.get("title"))
                .build();
    }

    public EmployeeDto map(final Employee employee) {
        /*return EmployeeDto.builder()
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
                .build();
*/
        // TODO What if we have null values in employee object
        // TODO Better way to do this
        Employee manager = employee.getManager();
        List<Employee> reports = employee.getReports();
        AssociatedEmployeeDetailsDto managerResponseDto;
        List<AssociatedEmployeeDetailsDto> reportResponseDto;
        EmployeeDto employeeResponseDto = EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .title(employee.getTitle())
                .build();
        if (manager != null) {
            managerResponseDto = AssociatedEmployeeDetailsDto.builder()
                    .id(manager.getId())
                    .name(manager.getName())
                    .title(manager.getTitle())
                    .build();
            employeeResponseDto.setManager(managerResponseDto);
        }
        if (reports != null) {
            reportResponseDto = new ArrayList<>();
            for (Employee report : reports) {
                reportResponseDto.add(
                        AssociatedEmployeeDetailsDto.builder()
                                .id(report.getId())
                                .name(report.getName())
                                .title(report.getTitle())
                                .build()
                );
            }
            employeeResponseDto.setReports(reportResponseDto);
        }
        return employeeResponseDto;
    }
}
