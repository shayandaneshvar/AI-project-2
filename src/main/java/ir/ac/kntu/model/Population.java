package ir.ac.kntu.model;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Population<T extends Chromosome> {
    protected List<T> chromosomes;
    protected final int populationSize;
    protected List<T> childChromosomes;
    protected String[] sentences;
    protected double[] sentenceScores;
    protected double[][] similarities;

    public Population(int populationSize, String text) {
        generateInitialPopulation();
        this.populationSize = populationSize;
        this.sentences = Chromosome.getSentences(text);
        this.sentenceScores = Chromosome.calculateSentenceScores(text, sentences);
        this.similarities = Chromosome.calculateSimilarities(sentences);

    }

    protected final void generateInitialPopulation() {
        for(int i = 0; i < populationSize; i++){
            T t = (T)T.newChromosome(sentences, sentenceScores, similarities);
            t.generate();
            chromosomes.add(t);
        }
    }

    protected final void calculateParentFitness() {
        chromosomes.forEach(Chromosome::calculateFitness);
    }

    protected abstract List<T> selectCandidateParents();//3

    protected abstract void crossover();//4

    protected abstract void mutate();//5

    protected final void calculateChildFitness() {
        childChromosomes.forEach(Chromosome::calculateFitness);
    }

    protected final void replacement(){
        List<T> newChromosomes = chromosomes.stream().filter(chromosome -> ThreadLocalRandom.current().nextBoolean())
                .collect(Collectors.toList());
        newChromosomes.addAll(childChromosomes.stream().filter(childChromosome -> ThreadLocalRandom.current()
                .nextBoolean()).collect(Collectors.toList()));
        Collections.sort(newChromosomes);
        chromosomes = newChromosomes.subList(0, populationSize);
        childChromosomes = null;
    }

}
