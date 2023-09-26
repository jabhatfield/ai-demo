package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpCategory;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Schema(name="ZooChatbotChatResponse", description="Model for returning replies and their associated probabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpCategoriseResponse {

    @Schema(description = "Categorical ID of the reply", required = true, example = "animal-list")
    private String category;

    @Schema(description = "Chatbot reply", required = true,
            example = "The zoo has 10 giraffes, 3 elephants, 2 lions and 12 penguins")
    private String reply;

    @ArraySchema(arraySchema = @Schema(description = "Probabilities of correctness", required = true,
            example = "[ { \"category\": \"animal-list\", \"probability\": 0.6893424773 }, " +
                    "{ \"category\": \"directions\", \"probability\": 0.0776643807 }, " +
                    "{ \"category\": \"opening-times\", \"probability\": 0.0776643807 } ]"))
    private List<OpenNlpCategory> probabilities;
}