package com.jonhatfield.aidemo.dto.helper;

import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotToken", description="Model for holding a token and its associated probability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpToken {
    @Schema(description = "A single token", required = true, example = "giraffes")
    private String token;

    @Schema(description = ResponseUtil.PROBABILITY_TEN_DP, required = true, example = "0.9925965989")
    private Double probability;
}