package com.jonhatfield.aidemo.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotLemmatizeRequest", description="Model of tokens to lemmatize")
@Getter
@Setter
@RequiredArgsConstructor
public class OpenNlpLemmatizeRequest {

    @ArraySchema(arraySchema = @Schema(description = "Tokens to lemmatize", required = true,
            example = "[\"any\", \"giraffes\", \"?\"]"))
    private String[] tokens;

    @ArraySchema(arraySchema = @Schema(description = "Part-of-speech tags", required = true,
            example = "[\"DT\",\"NNS\",\".\"]"))
    private String[] posTags;
}