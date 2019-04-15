package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponseDto {
    @JsonProperty("success_message")
    private String responseMessage;
}
