package com.jonhatfield.aidemo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ServerError", description="Server error code and message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerErrorDocumentation {

    @Schema(required = true, example = "100_UNKNOWN_ERROR")
    private String errorCode;

    @Schema(required = true, example = "Unknown error: Cannot invoke " +
            "\\\"com.jonhatfield.aidemo.dto.OpenNlpTokenizeResponse.getTokens()\\\" " +
            "because \\\"tokenizeResponse\\\" is null")
    private String errorMessage;
}
