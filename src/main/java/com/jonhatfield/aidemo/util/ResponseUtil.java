package com.jonhatfield.aidemo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class ResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("classpath:zoo-chat-responses.json")
    Resource zooChatResponsesFile;

    @Value("classpath:lemmatized-classification-data.txt")
    Resource intentDataFile;

    @Value("classpath:images")
    Resource imagesFolder;

    public String getResponse(String openNlpCategory) throws IOException {
        JsonNode jsonNode = getChatResponses();
        return jsonNode.get(openNlpCategory).asText();
    }

    public JsonNode getChatResponses() throws IOException {
        return mapper.readTree(zooChatResponsesFile.getFile());
    }

    public JsonNode getLemmatizedClassificationData() throws IOException {
        List<String> lines = FileUtils.readLines(intentDataFile.getFile(), StandardCharsets.UTF_8);
        Map<String, List<String>> responseMap = new HashMap<>();
        for(String line : lines) {
            String parts[] = line.split(" ", 2);
            if(!responseMap.containsKey(parts[0])) {
                List<String> entriesForNewCategory = new ArrayList<>();
                entriesForNewCategory.add(parts[1]);
                responseMap.put(parts[0], entriesForNewCategory);
            } else {
                responseMap.get(parts[0]).add(parts[1]);
            }
        }
        return mapper.valueToTree(responseMap);
    }

    public JsonNode getImages() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();
        List<String> sortedFileNames = new ArrayList<>();
        for(File f : imagesFolder.getFile().listFiles()) {
            sortedFileNames.add(f.getName());
        }
        Collections.sort(sortedFileNames);
        sortedFileNames.stream()
                .forEach(fileName -> arrayNode.add(fileName));
        return arrayNode;
    }

    public String getImageUri(String fileName) throws IOException {
        for(File f : imagesFolder.getFile().listFiles()) {
            if(f.getName().equals(fileName)) {
                return f.toURI().toString();
            }
        }
        throw new RuntimeException("File not found: " + fileName);//TODO exceptionclass and doc it test it
    }
}