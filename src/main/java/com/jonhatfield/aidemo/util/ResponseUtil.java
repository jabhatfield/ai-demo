package com.jonhatfield.aidemo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("classpath:zoo-chat-responses.json")
    Resource zooChatResponsesFile;

    public String getResponse(String openNlpCategory) throws IOException {
        JsonNode jsonNode = mapper.readTree(zooChatResponsesFile.getFile());
        return jsonNode.get(openNlpCategory).asText();
    }
}