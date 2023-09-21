package com.jonhatfield.aidemo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ClientError", description="Client error code and message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientErrorDocumentation {

    @Schema(required = true, example = "001_MISSING_FIELD")
    private String errorCode;

    @Schema(required = true, example = "Field 'message' is required")
    private String errorMessage;
}
