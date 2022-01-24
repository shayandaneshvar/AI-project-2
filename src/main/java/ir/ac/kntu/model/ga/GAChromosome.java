package ir.ac.kntu.model.ga;


import ir.ac.kntu.model.Chromosome;

import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

public class GAChromosome extends Chromosome {
    private final BitSet chromosome;
    private static final double alpha = 1;
    private static final double beta = 1;


    public GAChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        super(sentences, sentenceScores, similarities);
        chromosome = new BitSet(sentences.length);
    }

    public GAChromosome(GAChromosome gaChromosome, BitSet chromosome) {
        super(gaChromosome.sentences, gaChromosome.sentenceScores, gaChromosome.similarities);
        this.chromosome = chromosome;
    }

    @Override
    public void calculateFitness() {
        double scoreSum = 0;
        double similaritySum = 0;
        for (int i = 0; i < chromosome.length(); i++) {
            scoreSum += chromosome.get(i) ? super.sentenceScores[i] : 0;
            for (int j = 0; j < sentences.length; j++) {
                if(i == j){
                    continue;
                }
                similaritySum += similarities[i][j];
            }
        }
        fitness = alpha * scoreSum - beta * similaritySum;
    }

    public BitSet getChromosome() {
        return chromosome;
    }

    @Override
    public void mutate(){
        for (int i = 0; i < chromosome.length(); i++) {
            if(ThreadLocalRandom.current().nextBoolean()) {
                chromosome.set(i, ThreadLocalRandom.current().nextBoolean());
            }
        }
    }

    @Override
    public void generate() {
        for (int i = 0; i < chromosome.length(); i++) {
            chromosome.set(i, ThreadLocalRandom.current().nextBoolean());
        }
    }
}
