package com.jonhatfield.aidemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.EmptyArrayException;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.OpenNlpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Zoo Chatbot")
@Slf4j
@RequestMapping("/opennlp")
@RestController
public class OpenNlpController {

    private OpenNlpService openNlpService;

    @Autowired
    public OpenNlpController(OpenNlpService openNlpService) {
        this.openNlpService = openNlpService;
    }

    @PostMapping("/categorise")
    public OpenNlpCategoriseResponse categorise(@RequestBody OpenNlpTextRequest openNlpTextRequest) {
        if(StringUtils.isBlank(openNlpTextRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        return openNlpService.categorise(new String[]{openNlpTextRequest.getMessage()});
    }

    @PostMapping("/tokenize")
    public OpenNlpTokenizeResponse tokenize(@RequestBody OpenNlpTextRequest openNlpTextRequest) {
        if(StringUtils.isBlank(openNlpTextRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        return openNlpService.tokenize(openNlpTextRequest.getMessage());
    }

    @PostMapping("/tag-parts-of-speech")
    public OpenNlpPosResponse tagPartsOfSpeech(@RequestBody OpenNlpPosRequest openNlpPosRequest) {
        if(openNlpPosRequest.getTokens() == null) {
            throw new MissingFieldException("tokens");
        } else if(openNlpPosRequest.getTokens().length == 0) {
            throw new EmptyArrayException("tokens");
        }
        return openNlpService.tagPartsOfSpeech(openNlpPosRequest.getTokens());
    }

    @PostMapping("/lemmatize")
    public OpenNlpLemmatizeResponse lemmatize(@RequestBody OpenNlpLemmatizeRequest openNlpLemmatizeRequest) {
        if(openNlpLemmatizeRequest.getTokens() == null) {
            throw new MissingFieldException("tokens");
        } else if(openNlpLemmatizeRequest.getTokens().length == 0) {
            throw new EmptyArrayException("tokens");
        } else if(openNlpLemmatizeRequest.getPosTags() == null) {
            throw new MissingFieldException("posTags");
        } else if(openNlpLemmatizeRequest.getPosTags().length == 0) {
            throw new EmptyArrayException("posTags");
        }
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
    public OpenNlpPossibleChatResponsesResponse getPossibleChatResponses() {
        return openNlpService.getPossibleChatResponses();
    }

    @GetMapping("/lemmatized-classification-data")
    public OpenNlpLemmatizedInputDataResponse getLemmatizedClassificationData() {
        return openNlpService.getLemmatizedClassificationData();
    }
}