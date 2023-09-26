package com.jonhatfield.aidemo.dto.helper;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name="ZooChatbotReplyCategorisation", description="Model for returning categorised lemmatized data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpLemmatizedDataCategorisation {

    @Schema(description = "Categorical ID of the reply", required = true, example = "animal-list")
    private String category;

    @ArraySchema(arraySchema = @Schema(description = "Lemmatized phrases", required = true,
            example = "what animal do you have"))
    private List<String> lemmatizedPhrases;
}