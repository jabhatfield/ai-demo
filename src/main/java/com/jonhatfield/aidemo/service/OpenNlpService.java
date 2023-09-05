package com.jonhatfield.aidemo.service;

import com.jonhatfield.aidemo.dto.OpenNlpCategoriseResponse;
import com.jonhatfield.aidemo.dto.OpenNlpTokenizeResponse;
import com.jonhatfield.aidemo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.*;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class OpenNlpService {

    @Value("classpath:zoo-chat-intent-data.txt")
    Resource intentDataFile;

    @Value("classpath:en-token.bin")
    Resource tokenizerPretrainedModel;

    private ResponseUtil responseUtil;

    @Autowired
    public OpenNlpService(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    public OpenNlpCategoriseResponse categorise(String[] inputTokens) {
        DoccatModel model = null;

        try {
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(intentDataFile.getFile());
            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory,"UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters trainingParams = new TrainingParameters();
            trainingParams.put(TrainingParameters.ITERATIONS_PARAM, 100);//TODO config
            trainingParams.put(TrainingParameters.CUTOFF_PARAM, 0);

            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});

            model = DocumentCategorizerME.train("en", sampleStream, trainingParams, factory);

            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
            double[] outcomes = myCategorizer.categorize(inputTokens);
            String category = myCategorizer.getBestCategory(outcomes);

            String reply = responseUtil.getResponse(category);
            Map<String, Double> probabilities = myCategorizer.scoreMap(inputTokens);

            return new OpenNlpCategoriseResponse(category, reply, probabilities);
        } catch (Exception e) {
            log.error("error", e);
            return null;//TODO error handling
        }
    }

    public OpenNlpTokenizeResponse tokenize(String inputText) {
        try {
            InputStream modelIn = new FileInputStream(tokenizerPretrainedModel.getFile());
            TokenizerModel model = new TokenizerModel(modelIn);
            TokenizerME tokenizer = new TokenizerME(model);
            String[] tokens = tokenizer.tokenize(inputText);
            double[] probabilities = tokenizer.getTokenProbabilities();

            Map<String, Double> probabilityMap = new LinkedHashMap();
            for(int i = 0; i < tokens.length; i++) {
                probabilityMap.put(tokens[i], probabilities[i]);
            }

            return new OpenNlpTokenizeResponse(tokens, probabilityMap);
        } catch (Exception e) {
            log.error("error", e);
            return null;//TODO error handling
        }
    }
}