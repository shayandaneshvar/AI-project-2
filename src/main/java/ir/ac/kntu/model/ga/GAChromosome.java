package ir.ac.kntu.model.ga;


import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

public class GAChromosome extends Chromosome {
    private BitSet chromosome;

    public GAChromosome(String text) {
        super(text);
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
