package com.jonhatfield.aidemo.dto;

import com.jonhatfield.aidemo.dto.helper.DjlHandwrittenNumber;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name="HandwritingRecogniserClassificationResponse",
        description="Model for returning handwritten numbers and their associated probabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DjlImageClassificationResponse {

    @Schema(description = "Identified number", required = true, example = "0")
    private String handwrittenNumber;

    @ArraySchema(arraySchema = @Schema(description = "Probabilities of correctness", required = true,
            example = "[ { \"handwrittenNumber\": \"0\", \"probability\": 0.9999525547 }, " +
                    "{ \"handwrittenNumber\": \"2\", \"probability\": 4.18071E-5 }, " +
                    "{ \"handwrittenNumber\": \"6\", \"probability\": 2.9869E-6 } ]"))
    List<DjlHandwrittenNumber> probabilities;
}
