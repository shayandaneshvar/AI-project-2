package ir.ac.kntu.model.genetic;

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
        this.sentences = Chromosome.getSentences(text);
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

    protected final void calculateParentFitness() {
        chromosomes.forEach(Chromosome::calculateFitness);
    }

    protected List<T> selectCandidateParents() {
        Collections.sort(chromosomes);
        return chromosomes.subList(0, 2 + populationSize / 10);
    }

    protected abstract void crossover();//4


    protected void mutate() {
        childChromosomes.forEach(Chromosome::mutate);
    }

    protected final void calculateChildFitness() {
        childChromosomes.forEach(Chromosome::calculateFitness);
    }

    protected final void replacement() {
        List<T> newChromosomes = chromosomes.stream().filter(chromosome -> ThreadLocalRandom.current().nextBoolean())
                .collect(Collectors.toList());
        newChromosomes.addAll(childChromosomes.stream().filter(childChromosome -> ThreadLocalRandom.current()
                .nextBoolean()).collect(Collectors.toList()));
        Collections.sort(newChromosomes);
        chromosomes = newChromosomes.stream()
                .limit(populationSize)
                .collect(Collectors.toList());
        this.populationSize = chromosomes.size();
        childChromosomes = null;
    }

}
