package com.jonhatfield.aidemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.DjlService;
import com.jonhatfield.aidemo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Handwriting Recogniser")
@Slf4j
@RequestMapping("/handwriting-recogniser")
@RestController
public class DjlController {

    private DjlService djlService;

    @Autowired
    public DjlController(DjlService djlService) {
        this.djlService = djlService;
    }

    @Operation(summary = "Classify a handwritten number",
            description = "Classifies a handwritten number and provides the probability of correctness of each possible number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Client error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"errorCode\": \"001_MISSING_FIELD\", " +
                                    "\"errorMessage\": \"Field 'fileName' is required\" }"))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @PostMapping("/classify-handwritten-number")
    public DjlImageClassificationResponse classifyImage(@RequestBody DjlImageClassificationRequest djlImageClassificationRequest) {
        if(StringUtils.isBlank(djlImageClassificationRequest.getFileName())) {
            throw new MissingFieldException("fileName");
        }
        return djlService.classifyImage(djlImageClassificationRequest.getFileName());
    }

    @Operation(summary = "Classify a handwritten zero",
            description = "Classifies a handwritten zero and provides the probability of correctness of each possible number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @PostMapping("/classify-handwritten-zero")
    public DjlImageClassificationResponse classifyZeroImage() {
        return djlService.classifyZeroImage();
    }

    @Operation(summary = "List example input images",
            description = "Lists example input images of handwritten numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @GetMapping("/example-input-images")
    public DjlExampleInputImagesResponse getExampleInputImages() {
        return djlService.getExampleInputImages();
    }
}