package com.jonhatfield.aidemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.EmptyArrayException;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.DjlService;
import com.jonhatfield.aidemo.service.OpenNlpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("/classify-image")
    public DjlImageClassificationResponse classifyImage(@RequestBody DjlImageClassificationRequest djlImageClassificationRequest) {
        if(StringUtils.isBlank(djlImageClassificationRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        return djlService.classifyImage(djlImageClassificationRequest.getMessage());
    }

    @GetMapping("/example-input-images")
    public JsonNode getExampleInputImages() {
        return djlService.getExampleInputImages();
    }

    //TODO error handling, all code cleanup inc dlj
}