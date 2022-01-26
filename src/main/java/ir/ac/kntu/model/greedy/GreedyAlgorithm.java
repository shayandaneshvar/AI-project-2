package ir.ac.kntu.model.greedy;

import ir.ac.kntu.model.Algorithm;
import ir.ac.kntu.util.Util;

import java.util.Arrays;
import java.util.Comparator;

public class GreedyAlgorithm implements Algorithm {
    private final String[] sentences;
    private final int summaryLength;

    public GreedyAlgorithm(int summaryLength, String text) {
        this.sentences = Util.getSentences(text);
        this.summaryLength = summaryLength;
    }

    @Override
    public String call() {
        StringBuilder result = new StringBuilder();
        Arrays.stream(sentences)
                .sorted(Comparator.comparingInt(String::length))
                .forEachOrdered(z -> {
                    if (result.length() + z.length() <= summaryLength) {
                        result.append(z).append(".");
                    }
                });
        return result.toString();
    }
}
