
package movieclassifier;

import java.io.File;
import jsat.classifiers.*;
import jsat.classifiers.bayesian.NaiveBayesUpdateable;
import jsat.text.tokenizer.NaiveTokenizer;
import jsat.text.wordweighting.TfIdf;

/**
 * Hello world!
 */
public class Main  {
    /* From https://github.com/EdwardRaff/JSAT/wiki/Loading-text-data-and-Spam-Classification*/
    private static void trainClassifier() {
        /* Load training data(subtitle files) */
        File subtitleFile = new File("../../../../subtitles/action/Braveheart (1995) [ENG] [DVDrip] CD1.stripped");
        SubtitleLoader loader = new SubtitleLoader(subtitleFile, new NaiveTokenizer(), new TfIdf());
        ClassificationDataSet cds = loader.getDataSet();

        /* Print status */
        System.out.println("Data set loaded in");
        System.out.println(cds.getSampleSize() + " data points");
        System.out.println(cds.getNumNumericalVars() + " features");

        /* Classifying stuff */
        Classifier classifier = new NaiveBayesUpdateable(true);

        ClassificationModelEvaluation cme = new ClassificationModelEvaluation(classifier, cds);
        cme.evaluateCrossValidation(10);

        System.out.println("Total Training Time: " + cme.getTotalTrainingTime());
        System.out.println("Total Classification Time: " + cme.getTotalClassificationTime());
        System.out.println("Total Error rate: " + cme.getErrorRate());
        cme.prettyPrintConfusionMatrix();
    }
    
    /* */
    public static void main(String[] args) {
        System.out.println("Starting program...");
        
        //trainClassifier();
        Subtitle s = new Subtitle("subtitles/action/dollars.stripped", "Action");
        System.out.println("Program done...");
        
    }
}
