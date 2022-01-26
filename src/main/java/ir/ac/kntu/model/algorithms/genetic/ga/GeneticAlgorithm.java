package ir.ac.kntu.model.algorithms.genetic.ga;

import ir.ac.kntu.model.algorithms.genetic.BaseGeneticAlgorithm;

public class GeneticAlgorithm extends BaseGeneticAlgorithm<GAChromosome> {
    public GeneticAlgorithm(int summaryLimit, int populationSize, String text) {
        super(new GAPopulation(summaryLimit, populationSize, text));
    }
}
