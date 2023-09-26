package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpReplyCategorisation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name="ZooChatbotPossibleResponsesResponse", description="Model for returning replies and their categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpPossibleChatResponsesResponse {

    @ArraySchema(arraySchema = @Schema(description = "Replies and their categories", required = true,
            example = "[ { \"category\": \"opening-times\", " +
                    "\"reply\": \"The opening times are Mon - Fri 9am - 7pm every day, including holidays\" }, " +
                    "{ \"category\": \"animal-list\", " +
                    "\"reply\": \"The zoo has 10 giraffes, 3 elephants, 2 lions and 12 penguins\" }, " +
                    "{ \"category\": \"ticket-prices\", " +
                    "\"reply\": \"Tickets cost £20 for adults and £10 for children. Refunds are permitted 48 hours or more prior to entry time\" } ]"))
    List<OpenNlpReplyCategorisation> possibleResponses;
}