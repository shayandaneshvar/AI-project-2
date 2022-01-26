package ir.ac.kntu.model.algorithms.greedy;

import ir.ac.kntu.model.algorithms.Algorithm;
import ir.ac.kntu.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GreedyAlgorithm implements Algorithm {
    private final String[] sentences;
    private final int summaryLength;

    public GreedyAlgorithm(int summaryLength, String text) {
        this.sentences = Util.getSentences(text);
        this.summaryLength = summaryLength;
    }

    @Override
    public String call() {
        AtomicInteger chosenSentencesWords = new AtomicInteger();
        List<String> chosenSentences = new ArrayList<>();
        Arrays.stream(sentences)
                .sorted(Comparator.comparingInt(String::length))
                .forEachOrdered(z -> {
                    if (chosenSentencesWords.get() + z.split(" ").length < summaryLength) {
                        chosenSentences.add(z);
                        chosenSentencesWords.addAndGet(z.split(" ").length);
                    }
                });

        StringBuilder result = new StringBuilder();
        for (String sentence : sentences) {
            if (chosenSentences.contains(sentence)) {
                result.append(sentence.trim()).append(".\n");
            }
        }
        return result.toString();
    }
}
