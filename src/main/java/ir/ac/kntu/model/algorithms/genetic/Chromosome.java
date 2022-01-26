package ir.ac.kntu.model.algorithms.genetic;

import java.util.*;
import java.util.stream.Collectors;


public abstract class Chromosome implements Comparable<Chromosome> {
    protected final String[] sentences;
    protected final double[] sentenceScores;
    protected final double[][] similarities;
    protected static final double alpha = 1;
    protected static final double beta = 1;
    protected double fitness;

    public Chromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        this.sentenceScores = sentenceScores;
        this.sentences = sentences;
        this.similarities = similarities;
    }

    public abstract String getEquivalentText();

    public double getFitness() {
        return fitness;
    }

    public abstract void mutate();

    public abstract void calculateFitness();

    public abstract void generate();

    public static double[] calculateSentenceScores(String text, String[] sentences) {
        var sentenceScores = new double[sentences.length];
        text = text.trim().replaceAll("[,()\\[\\]\\-+=*@#&^%$!?.;:]", " ");
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
        for (int i = 0; i < sentences.length; i++) {
            for (int j = i + 1; j < sentences.length; j++) {
                Set<String> wordsI = Arrays.stream(sentences[i]
                        .replaceAll("[,()\\[\\]\\-+=*@#&^%$!?.;:]", " ").split(" "))
                        .collect(Collectors.toSet());
                Set<String> wordsJ = Arrays.stream(sentences[j].replaceAll("[,()\\[\\]\\-+=*@#&^%$!?.;:]", " ")
                        .split(" ")).collect(Collectors.toSet());

                long intersectionCount = wordsJ.parallelStream().filter(wordsI::contains).count();
                wordsI.addAll(wordsJ);

                long unionCount = wordsI.size();

                result[i][j] = ((float) intersectionCount) / unionCount;

                result[j][i] = result[i][j];

            }
        }
        return result;
    }

    @Override
    public int compareTo(Chromosome o) {
        return (int) ((-fitness + o.fitness) * 1e7);
    }


}
