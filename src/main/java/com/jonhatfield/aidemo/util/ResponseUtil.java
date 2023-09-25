package com.jonhatfield.aidemo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonhatfield.aidemo.dto.DjlExampleInputImagesResponse;
import com.jonhatfield.aidemo.dto.OpenNlpLemmatizedInputDataResponse;
import com.jonhatfield.aidemo.dto.helper.OpenNlpLemmatizedDataCategorisation;
import com.jonhatfield.aidemo.exception.ImageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class ResponseUtil {

    public static final String SERVER_ERROR_RESPONSE_EXAMPLE = "{ \"errorCode\": \"100_UNKNOWN_ERROR\", " +
            "\"errorMessage\": \"Cannot invoke \\\"String.toString()\\\" because \\\"response\\\" is null. See log for details\" }";

    public static final String PROBABILITY_TEN_DP = "Probability of correctness to 10 d.p.";

    private static final ObjectMapper mapper = new ObjectMapper();

    public String getResponse(String openNlpCategory) throws IOException {
        JsonNode jsonNode = getChatResponses();
        return jsonNode.get(openNlpCategory).asText();
    }

    public JsonNode getChatResponses() throws IOException {
        return mapper.readTree(getFileWithinJar("zoo-chat-responses.json"));
    }

    public File getFileWithinJar(String fileName) {
        ClassPathResource res = new ClassPathResource("src/main/resources/" + fileName);
        return new File(res.getPath());
    }

    public OpenNlpLemmatizedInputDataResponse getLemmatizedClassificationData() throws IOException {
        List<String> lines = FileUtils
                .readLines(getFileWithinJar("lemmatized-classification-data.txt"), StandardCharsets.UTF_8);
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
        List<OpenNlpLemmatizedDataCategorisation> entries = new ArrayList<>();
        for (String key : responseMap.keySet()) {
            entries.add(new OpenNlpLemmatizedDataCategorisation(key, responseMap.get(key)));
        }
        return new OpenNlpLemmatizedInputDataResponse(entries);
    }

    public DjlExampleInputImagesResponse getImages() throws IOException {
        List<String> sortedFileNames = new ArrayList<>();
        for(File f : getFileWithinJar("images").listFiles()) {
            sortedFileNames.add(f.getName());
        }
        Collections.sort(sortedFileNames);
        return new DjlExampleInputImagesResponse(sortedFileNames);
    }

    public String getImageUri(String fileName) throws IOException {
        for(File f : getFileWithinJar("images").listFiles()) {
            if(f.getName().equals(fileName)) {
                return f.toURI().toString();
            }
        }
        ImageNotFoundException e = new ImageNotFoundException(fileName);
        log.error("Image file not found", e);
        throw e;
    }

    public double roundDouble(Double d) {
        BigDecimal bd = BigDecimal.valueOf(d);
        bd = bd.setScale(10, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}