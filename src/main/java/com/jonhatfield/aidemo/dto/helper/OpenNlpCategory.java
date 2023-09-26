package com.jonhatfield.aidemo.dto.helper;

import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Schema(name="ZooChatbotCategory", description="Model for holding a reply category and its associated probability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpCategory {

    @Schema(description = "Categorical ID of the reply", required = true, example = "animal-list")
    private String category;

    @Schema(description = ResponseUtil.PROBABILITY_TEN_DP, required = true, example = "0.6893424773")
    private Double probability;
}