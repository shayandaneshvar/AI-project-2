package ir.ac.kntu.util;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class Utils {
    public static final String WORDS_ADDRESS = "words.txt";
    private static Set<String> words = null;

    public static Set<String> readWords() {
        if (words != null) return words;

        try (TextReader textReader = new TextReader(WORDS_ADDRESS)) {
            while (!textReader.endOfFile()) {
                String word = textReader.nextLine().toLowerCase();
                words.add(word);
            }
        } catch (Exception ignore) {
        }

        return words;
    }

    public static List<String> randomWords(int maxNumber) {
        readWords();
        double probability = (float) 0.9 * maxNumber / words.size();
        List<String> result = words.parallelStream()
                .filter(z -> ThreadLocalRandom.current().nextDouble(0, 1) < probability)
                .limit(maxNumber).collect(Collectors.toList());
        while (result.size() < maxNumber) {
            result.add(" ");
        }
        return result;
    }
}
