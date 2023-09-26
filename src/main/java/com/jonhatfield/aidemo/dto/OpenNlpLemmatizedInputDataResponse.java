package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.OpenNlpLemmatizedDataCategorisation;
import com.jonhatfield.aidemo.dto.helper.OpenNlpReplyCategorisation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name="ZooChatbotLemmatizedDataResponse", description="Model for returning the lemmatized training data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpLemmatizedInputDataResponse {

    @ArraySchema(arraySchema = @Schema(description = "Lemmatized training data", required = true,
            example = "[ { \"category\": \"animal-list\", \"lemmatizedPhrases\": [ \"what animal do you have\" ] }, " +
                    "{ \"category\": \"directions\", \"lemmatizedPhrases\": [ \"where be you\" ] }, " +
                    "{ \"category\": \"opening-times\", \"lemmatizedPhrases\": [ \"when be you open\" ] } ]"))
    List<OpenNlpLemmatizedDataCategorisation> lemmatizedData;
}