package ir.ac.kntu.model.ga;

import java.util.HashMap;
import java.util.Map;

public abstract class Chromosome {
    protected String[] sentences;
    protected final double[] sentenceScores;
    protected double fitness;
    protected double[][] similarities;

    public Chromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        this.sentenceScores = sentenceScores;
        this.sentences = sentences;
        this.similarities = similarities;
    }


    public abstract void calculateFitness();

    public abstract void generate();


    public static String[] getSentences(String text) {
        text = text.replace(".", ";")
                .replace("!", ";")
                .replace("?", ";");
        return text.split(";");
    }

    public static double[] calculateSentenceScores(String text, String[] sentences) {
        var sentenceScores = new double[sentences.length];
        text = text.trim().replace(".", ";")
                .replace("!", ";")
                .replace("?", ";")
                .replace(";", " ")
                .replaceAll("[,()\\[\\]\\-+=*@#&^%$]", " ");
//                .replace("(", " ")
//                .replace(")", " ")
//                .replace("[", " ")
//                .replace("]", " ")
//                .replace(",", " ");

        Map<String, Integer> frequencies = new HashMap<>();
        for (var word : text.split(" ")) {
            if (frequencies.containsKey(word)) {
                frequencies.put(word, frequencies.get(word) + 1);
                continue;
            }
            frequencies.put(word, 1);
        }

        for (int i = 0; i < sentences.length; i++) {
            int score = 0;
            for (Map.Entry<String, Integer> pair : frequencies.entrySet()) {
                if (sentences[i].contains(pair.getKey())) {
                    score++; // w1 w2 w3 w2. === w1 w2 w3
                }
            }
            sentenceScores[i] = score;
        }
        return sentenceScores;
    }

    public static double[][] calculateSimilarities(String[] sentences) {
        var result = new double[sentences.length][sentences.length];
        // TODO: 1/24/2022  

        return result;
    }
}
