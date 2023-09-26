package com.jonhatfield.aidemo.controller;

import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.exception.EmptyArrayException;
import com.jonhatfield.aidemo.exception.MissingFieldException;
import com.jonhatfield.aidemo.service.OpenNlpService;
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

@Tag(name = "Zoo Chatbot")
@Slf4j
@RequestMapping("/zoo-chatbot")
@RestController
public class OpenNlpController {

    private OpenNlpService openNlpService;

    @Autowired
    public OpenNlpController(OpenNlpService openNlpService) {
        this.openNlpService = openNlpService;
    }

    @Operation(summary = "Tokenize a message",
            description = "Tokenizes a message into an array of tokens and provides their probability of correctness")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Client error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{ \"errorCode\": \"001_MISSING_FIELD\", " +
                            "\"errorMessage\": \"Field 'message' is required\" }"))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @PostMapping("/tokenize")
    public OpenNlpTokenizeResponse tokenize(@RequestBody OpenNlpTokenizeRequest openNlpTokenizeRequest) {
        if(StringUtils.isBlank(openNlpTokenizeRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        return openNlpService.tokenize(openNlpTokenizeRequest.getMessage());
    }

    @Operation(summary = "Tag parts of speech",
            description = "Tags an array of tokens with part-of-speech and provides their probability of correctness")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Client error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{ \"errorCode\": \"001_MISSING_FIELD\", " +
                            "\"errorMessage\": \"Field 'tokens' is required\" }"))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @PostMapping("/tag-parts-of-speech")
    public OpenNlpPosResponse tagPartsOfSpeech(@RequestBody OpenNlpPosRequest openNlpPosRequest) {
        if(openNlpPosRequest.getTokens() == null) {
            throw new MissingFieldException("tokens");
        } else if(openNlpPosRequest.getTokens().length == 0) {
            throw new EmptyArrayException("tokens");
        }
        return openNlpService.tagPartsOfSpeech(openNlpPosRequest.getTokens());
    }

    @Operation(summary = "Lemmatize tokens",
            description = "Lemmatizes an array of tokens and provides their probability of correctness")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Client error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"errorCode\": \"001_MISSING_FIELD\", " +
                                    "\"errorMessage\": \"Field 'tokens' is required\" }"))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
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

    @Operation(summary = "Chat with the Zoo Chatbot",
            description = "Replies to a message and provides the probability of correctness of each possible reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Client error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"errorCode\": \"001_MISSING_FIELD\", " +
                                    "\"errorMessage\": \"Field 'message' is required\" }"))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @PostMapping("/chat")
    public OpenNlpCategoriseResponse chat(@RequestBody OpenNlpChatRequest openNlpChatRequest) {
        if(StringUtils.isBlank(openNlpChatRequest.getMessage())) {
            throw new MissingFieldException("message");
        }
        OpenNlpTokenizeResponse tokenizeResponse = openNlpService.tokenize(openNlpChatRequest.getMessage());
        String[] tokens = tokenizeResponse.getTokens();
        OpenNlpPosResponse posResponse = openNlpService.tagPartsOfSpeech(tokens);
        OpenNlpLemmatizeResponse lemmatizeResponse = openNlpService.lemmatize(tokens, posResponse.getTags());

        return openNlpService.categorise(lemmatizeResponse.getLemmas());
    }

    @Operation(summary = "List possible responses",
            description = "Lists all possible Chatbot responses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @GetMapping("/possible-chat-responses")
    public OpenNlpPossibleChatResponsesResponse getPossibleChatResponses() {
        return openNlpService.getPossibleChatResponses();
    }

    @Operation(summary = "List lemmatized classification data",
            description = "Lists the lemmatized classification data used for training the Chatbot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ResponseUtil.SERVER_ERROR_RESPONSE_EXAMPLE)))
    })
    @GetMapping("/lemmatized-classification-data")
    public OpenNlpLemmatizedInputDataResponse getLemmatizedClassificationData() {
        return openNlpService.getLemmatizedClassificationData();
    }
}