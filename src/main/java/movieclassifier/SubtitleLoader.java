
package movieclassifier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsat.classifiers.CategoricalData;
import jsat.text.ClassificationTextDataLoader;
import jsat.text.tokenizer.Tokenizer;
import jsat.text.wordweighting.WordWeighting;


/**
 * From https://github.com/EdwardRaff/JSAT/wiki/Loading-text-data-and-Spam-Classification
 */
public class SubtitleLoader extends ClassificationTextDataLoader {
    private static final Map<String, Integer> classKeys = new HashMap<String, Integer>();
    static {
        classKeys.put("action", 0);
        classKeys.put("other" , 1);
    }
    private File indexFile;

    /**
     *
     * @param indexFile the file for the index file
     * @param tokenizer the tokenizer to use
     * @param weighting the weighting to apply
     */
    public SubtitleLoader(File indexFile, Tokenizer tokenizer, WordWeighting weighting) {
        super(tokenizer, weighting);
        this.indexFile = indexFile;
    }


    @Override
    protected void setLabelInfo() {
        labelInfo = new CategoricalData(2);
        for(Entry<String, Integer> entry : classKeys.entrySet()) {
            labelInfo.setOptionName(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void initialLoad() {
        try {
            char[] buffer = new char[8192*2];

            /* Reader for subtitle file */
            BufferedReader br = new BufferedReader(new FileReader(indexFile));

            /* Store the vectors or whatever */
            StringBuilder dataFileText = new StringBuilder();

            /* Iterate through the contents of the subtitle file */
            String line;
            while((line = br.readLine()) != null) {
                /* Discover the words */
                String[] split = line.split("\\s+");
                File dataFile = new File("../../../../data/datafile.txt");
                if (dataFile.exists()) {
                    /* Read the data file */
                    FileReader dataFileReader = new FileReader(dataFile);

                    dataFileText.delete(0, dataFileText.length());


                    int readIn;
                    while ((readIn = dataFileReader.read(buffer)) >= 0) {
                        dataFileText.append(buffer, 0, readIn);
                    }

                    dataFileReader.close();
                    addOriginalDocument(dataFileText.toString(), classKeys.get(split[0]));
                } else {
                    System.out.println("File " + dataFile + " was not found. Make sure your virus scanner didn't remove it!");
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SubtitleLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SubtitleLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
