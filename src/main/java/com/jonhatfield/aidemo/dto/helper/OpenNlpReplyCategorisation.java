package com.jonhatfield.aidemo.dto.helper;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotReplyCategorisation", description="Model for returning a reply and its category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpReplyCategorisation {

    @Schema(description = "Categorical ID of the reply", required = true, example = "animal-list")
    private String category;

    @Schema(description = "Chatbot reply", required = true,
            example = "The zoo has 10 giraffes, 3 elephants, 2 lions and 12 penguins")
    private String reply;
}