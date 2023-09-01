package com.jonhatfield.aidemo.controller;

import com.jonhatfield.aidemo.dto.OpenAIDemoRequest;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@RequestMapping("/opennlp")
@RestController
public class OpenNlpController {

    @Value("${openai.model}")
    private String model;

    @Value("classpath:zoo-chat-intent-data.txt")
    Resource intentDataFile;

    @PostMapping("/chat")
    public String sendChatMessage(@RequestBody OpenAIDemoRequest openAIDemoRequest) {
        DoccatModel model = null;

        try {
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(intentDataFile.getFile());
            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory,"UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters trainingParams = new TrainingParameters();
            trainingParams.put(TrainingParameters.ITERATIONS_PARAM, 100);//TODO config
            trainingParams.put(TrainingParameters.CUTOFF_PARAM, 0);

            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});

            model = DocumentCategorizerME.train("en", sampleStream, trainingParams, new DoccatFactory());

            String inputText = "time";
            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
            double[] outcomes = myCategorizer.categorize(new String[]{inputText});
            String category = myCategorizer.getBestCategory(outcomes);
            return category;
        } catch (Exception e) {
            log.error("error", e);
            return "error";//TODO error handling
        }
    }
}