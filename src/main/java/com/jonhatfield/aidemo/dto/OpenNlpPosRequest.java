package com.jonhatfield.aidemo.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotPartsOfSpeechRequest", description="Model of tokens to tag with part-of-speech")
@Getter
@Setter
@RequiredArgsConstructor
public class OpenNlpPosRequest {

    @ArraySchema(arraySchema = @Schema(description = "Tokens to tag", required = true,
            example = "[\"any\", \"giraffes\", \"?\"]"))
    private String[] tokens;
}