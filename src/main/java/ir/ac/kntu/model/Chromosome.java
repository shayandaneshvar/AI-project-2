package ir.ac.kntu.model;

import java.util.*;


public abstract class Chromosome implements Comparable<Chromosome> {
    protected String[] sentences;
    protected final double[] sentenceScores;
    protected double fitness;
    protected double[][] similarities;
    protected static final double alpha = 1;
    protected static final double beta = 1;


    public Chromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        this.sentenceScores = sentenceScores;
        this.sentences = sentences;
        this.similarities = similarities;
    }

    public abstract void mutate();

    public static Chromosome newChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        throw new RuntimeException();
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





    public static double[][] calculateSimilarities(String[] sentences){
        var result = new double[sentences.length][sentences.length];
        for(int i = 0; i < sentences.length; i++){
            for (int j = i + 1 ; j < sentences.length; j++) {
                Set<String> wordsI = new HashSet<>(Set.of(sentences[i]
                        .replaceAll("[,()\\[\\]\\-+=*@#&^%$!?.;:]", " ").split(" ")));
                Set<String> wordsJ = Set.of(sentences[j].replaceAll("[,()\\[\\]\\-+=*@#&^%$!?.;:]", " ")
                        .split(" "));

                long intersectionCount = wordsJ.parallelStream().filter(wordsI::contains).count();
                wordsI.addAll(wordsJ);

                long unionCount = wordsI.size();

                result[i][j] = ((float)intersectionCount) / unionCount;

                result[j][i] = result[i][j];

            }
        }
        return result;
    }

    @Override
    public int compareTo(Chromosome o) {
        return (int)((- fitness + o.fitness) * 1e7);
    }


}
