package com.jonhatfield.aidemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.DjlImageClassificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DjlService {

    public DjlImageClassificationResponse classifyImage(String message) {
        return null;
    }

    public JsonNode getExampleInputImages() {
        return null;
    }
}