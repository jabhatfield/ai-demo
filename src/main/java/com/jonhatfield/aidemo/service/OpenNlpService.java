package com.jonhatfield.aidemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jonhatfield.aidemo.dto.*;
import com.jonhatfield.aidemo.dto.helper.*;
import com.jonhatfield.aidemo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class OpenNlpService {

    private ResponseUtil responseUtil;

    @Autowired
    public OpenNlpService(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    public OpenNlpCategoriseResponse categorise(String[] inputTokens) {
        try {
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(responseUtil
                            .getFileWithinJar("lemmatized-classification-data.txt"));
            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory,"UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters trainingParams = new TrainingParameters();
            trainingParams.put(TrainingParameters.ITERATIONS_PARAM, 100);//TODO config
            trainingParams.put(TrainingParameters.CUTOFF_PARAM, 0);

            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});

            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, trainingParams, factory);

            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
            double[] outcomes = myCategorizer.categorize(inputTokens);
            String category = myCategorizer.getBestCategory(outcomes);

            String reply = responseUtil.getResponse(category);
            Map<String, Double> scoreMap = myCategorizer.scoreMap(inputTokens);

            List<OpenNlpCategory> probabilities = new ArrayList<>();
            for (String key : scoreMap.keySet()) {
                probabilities.add(new OpenNlpCategory(key, scoreMap.get(key)));
            }

            return new OpenNlpCategoriseResponse(category, reply, probabilities);
        } catch (Exception e) {
            log.error("Categorisation error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public OpenNlpTokenizeResponse tokenize(String inputText) {
        try {
            InputStream modelIn = new FileInputStream(responseUtil.getFileWithinJar("en-token.bin"));
            TokenizerModel model = new TokenizerModel(modelIn);
            TokenizerME tokenizer = new TokenizerME(model);
            String[] tokens = tokenizer.tokenize(inputText);
            double[] probabilities = tokenizer.getTokenProbabilities();

            List<OpenNlpToken> tokenProbabilities = new ArrayList<>();
            for(int i = 0; i < tokens.length; i++) {
                tokenProbabilities.add(new OpenNlpToken(tokens[i], probabilities[i]));
            }

            return new OpenNlpTokenizeResponse(tokens, tokenProbabilities);
        } catch (Exception e) {
            log.error("Tokenization error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public OpenNlpPosResponse tagPartsOfSpeech(String[] tokens) {
        try {
            InputStream modelIn = new FileInputStream(responseUtil.getFileWithinJar("en-pos-maxent.bin"));
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);
            String tags[] = tagger.tag(tokens);
            double probabilities[] = tagger.probs();

            List<OpenNlpTag> tagProbabilities = new ArrayList<>();
            for(int i = 0; i < tags.length; i++) {
                tagProbabilities.add(new OpenNlpTag(tags[i], probabilities[i]));
            }

            return new OpenNlpPosResponse(tags, tagProbabilities);
        } catch (Exception e) {
            log.error("Parts-of-speech tagging error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public OpenNlpLemmatizeResponse lemmatize(String[] tokens, String[] posTags) {
        try {
            InputStream modelIn = new FileInputStream(responseUtil.getFileWithinJar("en-lemmatizer.bin"));
            LemmatizerModel model = new LemmatizerModel(modelIn);
            LemmatizerME lemmatizer = new LemmatizerME(model);
            String[] lemmas = lemmatizer.lemmatize(tokens, posTags);
            double probabilities[] = lemmatizer.probs();

            List<OpenNlpLemma> lemmaProbabilities = new ArrayList<>();
            for(int i = 0; i < lemmas.length; i++) {
                lemmaProbabilities.add(new OpenNlpLemma(lemmas[i], probabilities[i]));
            }

            return new OpenNlpLemmatizeResponse(lemmas, lemmaProbabilities);
        } catch (Exception e) {
            log.error("Lemmatization error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public OpenNlpPossibleChatResponsesResponse getPossibleChatResponses() {
        try {
            JsonNode chatResponsesJson = responseUtil.getChatResponses();
            List<OpenNlpReplyCategorisation> replyCategories = new ArrayList<>();
            chatResponsesJson.fieldNames().forEachRemaining(
                    category -> replyCategories.add(new OpenNlpReplyCategorisation(category,
                            chatResponsesJson.get(category).asText())));
            ;
            return new OpenNlpPossibleChatResponsesResponse(replyCategories);
        } catch (Exception e) {
            log.error("Chat responses retrieval error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public OpenNlpLemmatizedInputDataResponse getLemmatizedClassificationData() {
        try {
            return responseUtil.getLemmatizedClassificationData();
        } catch (Exception e) {
            log.error("Lemmatization data retrieval error", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}