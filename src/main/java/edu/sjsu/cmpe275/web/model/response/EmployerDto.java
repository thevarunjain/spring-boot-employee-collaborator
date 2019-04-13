package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty(value = "address")
    private AddressDto address;

}
