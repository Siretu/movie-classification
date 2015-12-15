package movieclassifier;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subtitle {
    public String genre;
    Map<String, Double> occurrences;


    public Subtitle (String filename, String genre) {
        this.genre = genre;
        occurrences = new HashMap<String, Double>();
        parseSubtitles(filename);
        printOccurrences();
        normalizeOccurrences();
        printOccurrences();
    }

    private void normalizeOccurrences() {
        int total = 0;
        for (Double d : occurrences.values()) {
            total += d;
        }
        for (String key : occurrences.keySet()) {
            double normalizedValue = occurrences.get(key) / total;
            occurrences.put(key, normalizedValue);
        }
    }

    private void parseSubtitles(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
            for (String line : lines) {
                for (String word : line.split(" ")) {
                    word = stripWord(word);
                    Double previousValue = occurrences.get(word);
                    occurrences.put(word, previousValue == null ? 1 : previousValue + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printOccurrences() {
        String max = "";
        for (String key : occurrences.keySet()) {

            if (occurrences.get(key) > 0) {
                if (max.equals("") || occurrences.get(key) > occurrences.get(max)) {
                    max = key;
                }
                System.out.printf("%s: %f\n", key, occurrences.get(key));
            }
        }
        System.out.printf("Max: %s: %f\n", max, occurrences.get(max));
    }

    /**
     * Strip word of any silly punctuation like ',' '.' '!' '?' etc
     * Currently does nothing.
     */
    private String stripWord(String word) {
        // Accept alphabetical letters and apostrophes.
        return word.replaceAll("[^a-zA-Z']", "").toLowerCase();

    }
}
