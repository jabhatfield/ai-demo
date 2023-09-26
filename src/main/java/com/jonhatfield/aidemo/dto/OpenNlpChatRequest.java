package com.jonhatfield.aidemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name="ZooChatbotChatRequest", description="Model for sending a chat message")
@Getter
@Setter
@RequiredArgsConstructor
public class OpenNlpChatRequest {

    @Schema(description = "Chat message", required = true, example = "any giraffes?")
    String message;
}