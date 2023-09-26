package com.jonhatfield.aidemo.dto.helper;

import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotLemma", description="Model for holding a lemma and its associated probability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpLemma {

    @Schema(description = "A single lemma", required = true, example = "giraffe")
    private String lemma;

    @Schema(description = ResponseUtil.PROBABILITY_TEN_DP, required = true, example = "0.9917771516")
    private Double probability;
}