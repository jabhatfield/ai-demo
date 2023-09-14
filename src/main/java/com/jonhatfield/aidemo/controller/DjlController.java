package com.jonhatfield.aidemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.DjlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/djl")
@RestController
public class DjlController {

    private DjlService djlService;

    @Autowired
    public DjlController(DjlService djlService) {
        this.djlService = djlService;
    }

    @PostMapping("/classify-handwritten-number")
    public DjlImageClassificationResponse classifyImage(@RequestBody DjlImageClassificationRequest djlImageClassificationRequest) {
        if(StringUtils.isBlank(djlImageClassificationRequest.getFileName())) {
            throw new MissingFieldException("fileName");
        }
        return djlService.classifyImage(djlImageClassificationRequest.getFileName());
    }

    @PostMapping("/classify-handwritten-zero")
    public DjlImageClassificationResponse classifyZeroImage() {
        return djlService.classifyZeroImage();
    }

    @GetMapping("/example-input-images")
    public JsonNode getExampleInputImages() {
        return djlService.getExampleInputImages();
    }

    //TODO error handling, all code cleanup inc dlj
}