package com.jonhatfield.aidemo.dto.helper;

import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotToken", description="Model for holding a part-of-speech tag and its associated probability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpTag {

    @Schema(description = "A single tag", required = true, example = "NNS")
    private String tag;

    @Schema(description = ResponseUtil.PROBABILITY_TEN_DP, required = true, example = "0.9201411555")
    private Double probability;
}