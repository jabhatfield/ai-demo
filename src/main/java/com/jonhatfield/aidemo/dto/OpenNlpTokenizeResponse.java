package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpTag;
import com.jonhatfield.aidemo.dto.helper.OpenNlpToken;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

@Schema(name="ZooChatbotTokenizeResponse", description="Model for returning tokens and their associated probabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpTokenizeResponse {
    @ArraySchema(arraySchema = @Schema(description = "Tokenized message", required = true,
            example = "[\"any\", \"giraffes\", \"?\"]"))
    private String[] tokens;

    @ArraySchema(arraySchema = @Schema(description = "Probabilities of correctness", required = true,
            example = "[ { \"token\": \"any\", \"probability\": 1.0 }, " +
            "{ \"token\": \"giraffes\", \"probability\": 0.9925965989 }, " +
            "{ \"token\": \"?\", \"probability\": 1.0 } ]"))
    List<OpenNlpToken> probabilities;
}
