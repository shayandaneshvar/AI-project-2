package ir.ac.kntu.model.ga;


import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

public class GAChromosome extends Chromosome {
    private BitSet chromosome;


    public GAChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        super(sentences, sentenceScores, similarities);
        chromosome = new BitSet(sentences.length);
    }

    @Override
    public void calculateFitness() {

    }

    @Override
    public void generate() {
        for (int i = 0; i < chromosome.length(); i++) {
            chromosome.set(i, ThreadLocalRandom.current().nextBoolean());
        }
    }
}
