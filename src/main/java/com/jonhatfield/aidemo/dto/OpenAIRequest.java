package com.jonhatfield.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OpenAIRequest {

    private String model;
    private List<OpenAIMessage> messages;

    public OpenAIRequest(String model, String chatMessage) {
        this.model = model;
        this.messages = Arrays.asList(new OpenAIMessage("user", chatMessage));
    }
}
