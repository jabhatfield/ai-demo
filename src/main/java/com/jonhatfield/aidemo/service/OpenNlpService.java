package com.jonhatfield.aidemo.service;

import com.jonhatfield.aidemo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenNlpService {

    @Value("classpath:zoo-chat-intent-data.txt")
    Resource intentDataFile;

    private ResponseUtil responseUtil;

    @Autowired
    public OpenNlpService(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    public String categorise(String[] inputTokens) {
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
            //myCategorizer.scoreMap() todo stats output
            log.info(myCategorizer.scoreMap(inputTokens).toString());

            return responseUtil.getResponse(category);
        } catch (Exception e) {
            log.error("error", e);
            return "error";//TODO error handling
        }
    }
}