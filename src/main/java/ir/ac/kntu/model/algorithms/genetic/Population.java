package ir.ac.kntu.model.algorithms.genetic;

import ir.ac.kntu.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Population<T extends Chromosome> {
    protected List<T> chromosomes = new ArrayList<>();
    protected List<T> childChromosomes = new ArrayList<>();
    protected String[] sentences;
    protected double[] sentenceScores;
    protected double[][] similarities;
    protected int populationSize;
    private final int summaryLimit;

    public Population(int summaryLimit, int populationSize, String text) {
        this.populationSize = populationSize;
        this.sentences = Util.getSentences(text);
        this.sentenceScores = Chromosome.calculateSentenceScores(text, sentences);
        this.similarities = Chromosome.calculateSimilarities(sentences);
        this.summaryLimit = summaryLimit;
    }

    public abstract T newChromosome(String[] sentences, double[] sentenceScores, double[][] similarities);

    protected final void generateInitialPopulation() {
        for (int i = 0; i < populationSize; i++) {
            T t = newChromosome(sentences, sentenceScores, similarities);
            t.generate();
            if (t.getEquivalentText().trim().split(" ").length > summaryLimit) {
                i--;
                continue;
            }
            chromosomes.add(t);
        }
    }

    public String getBestChromosome() {
        return chromosomes.stream()
                .sorted()
                .findFirst()
                .map(Chromosome::getEquivalentText)
                .orElse("");
    }

    public double getBestFitness() {
        return chromosomes.stream()
                .sorted()
                .findFirst().map(Chromosome::getFitness).orElse(-1d);
    }

    protected final void calculateParentFitness() {
        chromosomes.forEach(Chromosome::calculateFitness);
    }

    protected List<T> selectCandidateParents() {
        Collections.sort(chromosomes);
        return chromosomes.stream()
                .limit(Math.min(populationSize , chromosomes.size() / 2))
                .collect(Collectors.toList());
    }

    protected abstract void crossover();//4


    protected void mutate() {
        childChromosomes.forEach(t -> {
            if (ThreadLocalRandom.current().nextBoolean())
                t.mutate();
        });
    }

    protected final void calculateChildFitness() {
        childChromosomes.forEach(Chromosome::calculateFitness);
    }

    protected final void replacement() {
        List<T> newChromosomes = chromosomes.stream()
                .filter(chromosome -> ThreadLocalRandom.current().nextBoolean())
                .collect(Collectors.toList());
        newChromosomes.addAll(childChromosomes.stream()
                .filter(childChromosome -> ThreadLocalRandom.current()
                .nextBoolean()).collect(Collectors.toList()));
        Collections.sort(newChromosomes);
        chromosomes = newChromosomes.stream()
                .limit(newChromosomes.size() / 2)
                .collect(Collectors.toList());
        childChromosomes = new ArrayList<>();
    }

    public long getCurrentPopulationSize(){
        return chromosomes.size();
    }
}
