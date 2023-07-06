package com.jonhatfield.aidemo.controller;

import com.jonhatfield.aidemo.dto.OpenAIDemoRequest;
import com.jonhatfield.aidemo.dto.OpenAIRequest;
import com.jonhatfield.aidemo.dto.OpenAIResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RequestMapping("/openai")
@RestController
public class OpenAIController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String url;

    @PostMapping("/chat")
    public String sendChatMessage(@RequestBody OpenAIDemoRequest openAIDemoRequest) {
        String model = StringUtils.isNotBlank(openAIDemoRequest.getModel()) ? openAIDemoRequest.getModel() : this.model;
        OpenAIRequest request = new OpenAIRequest(model, openAIDemoRequest.getMessage());

        OpenAIResponse openAIResponse = restTemplate.postForObject(url, request, OpenAIResponse.class);

        return openAIResponse.getChoices().get(0).getMessage().getContent();
    }
}