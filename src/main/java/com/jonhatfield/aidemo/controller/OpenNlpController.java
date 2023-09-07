package com.jonhatfield.aidemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.OpenNlpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/opennlp")
@RestController
public class OpenNlpController {

    @Value("${openai.model}")
    private String model;

    private OpenNlpService openNlpService;

    @Autowired
    public OpenNlpController(OpenNlpService openNlpService) {
        this.openNlpService = openNlpService;
    }

    @PostMapping("/categorise")
    public OpenNlpCategoriseResponse categorise(@RequestBody OpenNlpTextRequest openNlpTextRequest) {
        return openNlpService.categorise(new String[]{openNlpTextRequest.getMessage()});
    }

    @PostMapping("/tokenize")
    public OpenNlpTokenizeResponse tokenize(@RequestBody OpenNlpTextRequest openNlpTextRequest) {
        return openNlpService.tokenize(openNlpTextRequest.getMessage());
    }

    @PostMapping("/tag-parts-of-speech")
    public OpenNlpPosResponse tagPartsOfSpeech(@RequestBody OpenNlpPosRequest openNlpPosRequest) {
        return openNlpService.tagPartsOfSpeech(openNlpPosRequest.getTokens());
    }

    @PostMapping("/lemmatize")
    public OpenNlpLemmatizeResponse lemmatize(@RequestBody OpenNlpLemmatizeRequest openNlpLemmatizeRequest) {
        return openNlpService.lemmatize(openNlpLemmatizeRequest.getTokens(), openNlpLemmatizeRequest.getPosTags());
    }

    @PostMapping("/chat")
    public OpenNlpCategoriseResponse chat(@RequestBody OpenNlpTextRequest openNlpTextRequest) {
        if(StringUtils.isBlank(openNlpTextRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        OpenNlpTokenizeResponse tokenizeResponse = openNlpService.tokenize(openNlpTextRequest.getMessage());
        String[] tokens = tokenizeResponse.getTokens();
        OpenNlpPosResponse posResponse = openNlpService.tagPartsOfSpeech(tokens);
        OpenNlpLemmatizeResponse lemmatizeResponse = openNlpService.lemmatize(tokens, posResponse.getTags());

        return openNlpService.categorise(lemmatizeResponse.getLemmas());
    }

    @GetMapping("/possible-chat-responses")
    public JsonNode getPossibleChatResponses() {
        return openNlpService.getPossibleChatResponses();
    }

    @GetMapping("/lemmatized-classification-data")
    public JsonNode getLemmatizedClassificationData() {
        return openNlpService.getLemmatizedClassificationData();
    }

    //TODO error handling - code, OPENNLP_ERROR_001, message inc how to fix and eg, impl get lzied class data()
    //all code cleanup
}