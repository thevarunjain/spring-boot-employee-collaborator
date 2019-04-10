package edu.sjsu.cmpe275.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {

    private String responseCode;

    private String responseMessage;

    private String reference;
}
