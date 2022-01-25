package ir.ac.kntu.model.gp;

import ir.ac.kntu.model.Chromosome;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class GPChromosome extends Chromosome {

    private final TreeSet<Integer> chromosome;

    public GPChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        super(sentences, sentenceScores, similarities);
        chromosome = new TreeSet<>();
    }

    public GPChromosome(GPChromosome gpChromosome, TreeSet<Integer> chromosome) {
        super(gpChromosome.sentences, gpChromosome.sentenceScores, gpChromosome.similarities);
        this.chromosome = chromosome;
    }

    @Override
    public void mutate() {
        for (int i = 0; i < sentences.length; i++) {
            if(ThreadLocalRandom.current().nextBoolean()){
                if(ThreadLocalRandom.current().nextBoolean()){
                    chromosome.add(i);
                } else {
                    chromosome.remove(i);
                }
            }
        }
    }

    @Override
    public void calculateFitness() {
        double scoreSum = 0;
        double similaritySum = 0;
        for(Integer chr:chromosome) {
            scoreSum += super.sentenceScores[chr];

            for(Integer chrJ:chromosome){
                if(chr.equals(chrJ)){
                    continue;
                }
                similaritySum += similarities[chr][chrJ];
            }
        }
        fitness = alpha * scoreSum - beta * similaritySum;
    }

    public TreeSet<Integer> getChromosome(){
        return chromosome;
    }

    @Override
    public void generate() {
        for (int i = 0; i < sentences.length; i++) {
            if(ThreadLocalRandom.current().nextBoolean()){
                chromosome.add(i);
            }
        }
    }




}
