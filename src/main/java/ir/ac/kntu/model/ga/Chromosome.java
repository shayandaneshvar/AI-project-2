package ir.ac.kntu.model.ga;

import ir.ac.kntu.util.Utils;

import java.util.List;

public class Chromosome {
    private String[] words;
    private double fitness;

    public Chromosome(int length) {
        words = new String[length];
        List<String> generatedWords = Utils.randomWords(length);
        for (int i = 0; i < generatedWords.size(); i++) {
            words[i] = generatedWords.get(i);
        }
    }

    public void calculateFitness(){

    }


}
