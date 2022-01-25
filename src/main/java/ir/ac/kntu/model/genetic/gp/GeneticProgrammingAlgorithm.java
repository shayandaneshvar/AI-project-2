package ir.ac.kntu.model.genetic.gp;

import ir.ac.kntu.model.genetic.BaseGeneticAlgorithm;

public class GeneticProgrammingAlgorithm extends BaseGeneticAlgorithm<GPChromosome> {
    public GeneticProgrammingAlgorithm(int summaryLimit, int length, String text) {
        super(new GPPopulation(summaryLimit, length, text));
    }
}
