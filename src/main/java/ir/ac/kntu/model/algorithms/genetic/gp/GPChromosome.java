package ir.ac.kntu.model.algorithms.genetic.gp;

import ir.ac.kntu.model.algorithms.genetic.Chromosome;
import ir.ac.kntu.util.Util;

import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class GPChromosome extends Chromosome {

    private final TreeSet<Integer> chromosome;

    public GPChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        super(sentences, sentenceScores, similarities);
        chromosome = new TreeSet<>();
    }


    @Override
    public String getEquivalentText() {
        return chromosome.stream()
                .map(z -> sentences[z])
                .reduce((x, y) -> x.trim() + ".\n" + y.trim())
                .orElse("") + ".";
    }

    public GPChromosome(GPChromosome gpChromosome, TreeSet<Integer> chromosome) {
        super(gpChromosome.sentences, gpChromosome.sentenceScores, gpChromosome.similarities);
        this.chromosome = chromosome;
    }

    @Override
    public void mutate() {
        for (int i = 0; i < sentences.length; i++) {
            if (Util.action(1d/chromosome.size())) {
                if(chromosome.contains(i)){
                    chromosome.remove(i);
                }else {
                    chromosome.add(i);
                }
            }
        }
    }

    @Override
    public void calculateFitness() {
        double scoreSum = 0;
        double similaritySum = 0;
        for (Integer chr : chromosome) {
            scoreSum += super.sentenceScores[chr];

            for (Integer chrJ : chromosome) {
                if (chr.equals(chrJ) || !chromosome.contains(chrJ)) {
                    continue;
                }
                similaritySum += similarities[chr][chrJ];
            }
        }
        fitness = alpha * scoreSum - beta * similaritySum;
    }

    public TreeSet<Integer> getChromosome() {
        return chromosome;
    }

    @Override
    public void generate() {
        for (int i = 0; i < sentences.length; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                chromosome.add(i);
            }
        }
    }

}
