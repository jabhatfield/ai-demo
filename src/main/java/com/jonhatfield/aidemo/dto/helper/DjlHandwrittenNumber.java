package com.jonhatfield.aidemo.dto.helper;

import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotCategory", description="Model for holding a handwritten number and its associated probability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DjlHandwrittenNumber {

    @Schema(description = "Handwritten number", required = true, example = "0")
    private String handwrittenNumber;

    @Schema(description = ResponseUtil.PROBABILITY_TEN_DP, required = true, example = "0.9994780421")
    private Double probability;
}
