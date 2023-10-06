package com.jonhatfield.aidemo.service;

import ai.djl.Model;
import ai.djl.basicdataset.cv.classification.Mnist;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Activation;
import ai.djl.nn.Blocks;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.EasyTrain;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.Dataset;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import com.jonhatfield.aidemo.dto.DjlExampleInputImagesResponse;
import com.jonhatfield.aidemo.dto.DjlImageClassificationResponse;
import com.jonhatfield.aidemo.dto.helper.DjlHandwrittenNumber;
import com.jonhatfield.aidemo.exception.ImageNotFoundException;
import com.jonhatfield.aidemo.util.ImageTranslator;
import com.jonhatfield.aidemo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DjlService {

    private static Model CACHED_MODEL = null;

    private ResponseUtil responseUtil;

    @Autowired
    public DjlService(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    public DjlImageClassificationResponse classifyZeroImage() {
        return classifyImage("0.png");
    }

    public DjlImageClassificationResponse classifyImage(String fileName) {
        try {
            Model mlpModel;
            if(CACHED_MODEL != null) {
                log.info("Loading cached model");
                mlpModel = CACHED_MODEL;
            } else {
                log.info("Building new model");
                mlpModel = Model.newInstance("mlp");

                //prepare data - load dataset
                int batchSize = 32;
                Mnist trainingDataset = Mnist.builder()
                        .optUsage(Dataset.Usage.TRAIN)
                        .setSampling(batchSize, false)
                        .build();

                //build neural network
                long inputSize = Mnist.IMAGE_HEIGHT * Mnist.IMAGE_WIDTH;
                long outputSize = 10;
                SequentialBlock block = new SequentialBlock();
                block.add(Blocks.batchFlattenBlock(inputSize));
                block.add(Linear.builder().setUnits(128).build());
                block.add(Activation::relu);
                block.add(Linear.builder().setUnits(64).build());
                block.add(Activation::relu);
                block.add(Linear.builder().setUnits(outputSize).build());

                mlpModel.setBlock(block);

                //train the model
                DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss())
                        .addEvaluator(new Accuracy())
                        .addTrainingListeners(TrainingListener.Defaults.logging());

                Trainer trainer = mlpModel.newTrainer(config);

                int trainingParamsInitializationBatchSize = 1;
                trainer.initialize(new Shape(trainingParamsInitializationBatchSize, inputSize));
                int epochs = 2;
                EasyTrain.fit(trainer, epochs, trainingDataset, null);

                CACHED_MODEL = mlpModel;
            }

            //classify image
            Image inputImage = ImageFactory.getInstance().fromInputStream(responseUtil.getImageInputStream(fileName));

            ImageTranslator imageTranslator = new ImageTranslator();
            Predictor<Image, Classifications> predictor = mlpModel.newPredictor(imageTranslator);
            Classifications predictions = predictor.predict(inputImage);

            List<DjlHandwrittenNumber> probabilities = new ArrayList<>();
            for(Classifications.Classification classification : predictions.topK()) {
                probabilities.add(new DjlHandwrittenNumber(classification.getClassName(),
                        responseUtil.roundDouble(classification.getProbability())));
            }

            return new DjlImageClassificationResponse(predictions.best().getClassName(), probabilities);
        } catch (ImageNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Image classification error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public DjlExampleInputImagesResponse getExampleInputImages() {
        try {
            return responseUtil.getImages();
        } catch (Exception e) {
            log.error("Image listing error", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}