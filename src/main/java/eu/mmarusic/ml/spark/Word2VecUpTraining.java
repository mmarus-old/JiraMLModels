
package eu.mmarusic.ml.spark;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 9/23/19.
 */
public class Word2VecUpTraining {

//    //Click:
//    IntelliJ Preferences > Compiler > Command Line Options
//    //Then paste:
//    -Xms1024m
//    -Xmx10g
//    -XX:MaxPermSize=2g

    private static Logger log = LoggerFactory.getLogger(Word2VecUpTraining.class);

    public static Word2Vec uptrainModel(String path, SentenceIterator iterator) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel(new File(path));
        TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        word2Vec.setTokenizerFactory(tokenizerFactory);
        word2Vec.setSentenceIterator(iterator);

        log.info("Word2vec uptraining...");

        word2Vec.fit();

        log.info("Word2vec uptraining finished...");

        return word2Vec;
    }

}

