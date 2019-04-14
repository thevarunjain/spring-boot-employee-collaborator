package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("title")
    private String title;

    @JsonProperty(value = "address")
    private AddressDto address;

    @JsonProperty(value = "employer")
    private EmployerDto employer;

    @JsonProperty(value = "manager")
    private AssociatedEmployeeDetailsDto manager;

    @JsonProperty(value = "reports")
    @Singular
    private List<AssociatedEmployeeDetailsDto> reports;

    @JsonProperty(value = "collaborators")
    @Singular
    private List<CollaboratorDto> collaborators;

}
