package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpLemma;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name="ZooChatbotLemmatizeResponse", description="Model for returning lemmas and their associated probabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpLemmatizeResponse {

    @ArraySchema(arraySchema = @Schema(description = "Tokens to lemmatize", required = true,
            example = "[\"any\", \"giraffes\", \"?\"]"))
    private String[] lemmas;

    @ArraySchema(arraySchema = @Schema(description = "Probabilities of correctness", required = true,
            example = "[ { \"lemma\": \"any\", \"probability\": 0.9965145882 }, " +
            "{ \"lemma\": \"giraffe\", \"probability\": 0.9917771516 }, " +
            "{ \"lemma\": \"?\", \"probability\": 0.4033399929 } ]"))
    List<OpenNlpLemma> probabilities;
}