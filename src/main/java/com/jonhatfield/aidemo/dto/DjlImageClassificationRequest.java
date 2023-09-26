package com.jonhatfield.aidemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name="HandwritingRecogniserClassificationRequest",
        description="Model for sending a handwritten number to recognise")
@Getter
@Setter
@RequiredArgsConstructor
public class DjlImageClassificationRequest {

    @Schema(description = "Filename of the handwritten image", required = true, example = "0.png")
    String fileName;
}