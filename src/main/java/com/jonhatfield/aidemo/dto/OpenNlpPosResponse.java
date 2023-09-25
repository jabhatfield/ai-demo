package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpTag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

@Schema(name="ZooChatbotPartsOfSpeechResponse", description="Model for returning tagged tokens and their associated probabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpPosResponse {

    @ArraySchema(arraySchema = @Schema(description = "Part-of-speech tags", required = true,
            example = "[\"DT\",\"NNS\",\".\"]"))
    private String[] tags;

    @ArraySchema(arraySchema = @Schema(description = "Probabilities of correctness", required = true,
            example = "[ { \"tag\": \"DT\", \"probability\": 0.9201411555 }, " +
            "{ \"tag\": \"NNS\", \"probability\": 0.867740459 }, " +
            "{ \"tag\": \".\", \"probability\": 0.982848614 } ]"))
    List<OpenNlpTag> probabilities;
}
