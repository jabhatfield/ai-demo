package com.jonhatfield.aidemo.controller;

import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.service.OpenNlpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //todo allow only first word for this API call
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

    //TODO lemma, all together, error handling - code, OPENNLP_ERROR_001, message inc how to fix and eg
    //more data categories, ticket prices adult child family membership, directions, contact details total
    //5 categories
}