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

    public final void generateInitialPopulation() {
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

    public final void calculateParentFitness() {
        chromosomes.forEach(Chromosome::calculateFitness);
    }

    protected List<T> selectCandidateParents() {
        Collections.sort(chromosomes);
        return chromosomes.stream()
                .filter(t -> t.getEquivalentText().trim().split(" ").length > summaryLimit)
                .limit(populationSize)
                .collect(Collectors.toList());
    }

    protected abstract void crossover();//4


    public void mutate() {
        childChromosomes.forEach(t -> {
            if (ThreadLocalRandom.current().nextBoolean())
                t.mutate();
        });
    }

    public final void calculateChildFitness() {
        childChromosomes.forEach(Chromosome::calculateFitness);
    }

    protected void replacement() {
        List<T> newChromosomes = new ArrayList<>(chromosomes);
        newChromosomes.addAll(childChromosomes);
        Collections.sort(newChromosomes);
        while (newChromosomes.size() > populationSize) {
            int index = ThreadLocalRandom.current().nextInt(0, newChromosomes.size());
            newChromosomes.remove(index);
        }
        childChromosomes = new ArrayList<>();
    }

    public long getCurrentPopulationSize() {
        return chromosomes.size();
    }

    public final List<T> getChromosomes() {
        return chromosomes;
    }

    protected int getSummaryLimit() {
        return summaryLimit;
    }
}
