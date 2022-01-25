package ir.ac.kntu.model.genetic.ga;

import ir.ac.kntu.model.genetic.BaseGeneticAlgorithm;

public class GeneticAlgorithm extends BaseGeneticAlgorithm<GAChromosome> {
    public GeneticAlgorithm(int summaryLimit, int populationSize, String text) {
        super(new GAPopulation(summaryLimit, populationSize, text));
    }
}
