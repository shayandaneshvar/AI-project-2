package ir.ac.kntu.model.genetic;

import ir.ac.kntu.model.Algorithm;

import java.util.stream.IntStream;

public abstract class BaseGeneticAlgorithm<CHROMOSOME extends Chromosome>
        implements Algorithm {

    private Population<CHROMOSOME> population;
    private static final int maxGenerations = 20;

    public BaseGeneticAlgorithm(Population<CHROMOSOME> population) {
        this.population = population;
    }

    @Override
    public String call() {
        population.generateInitialPopulation();// 1
        IntStream.range(0, maxGenerations).forEach(z -> {
            population.calculateParentFitness(); // 2
            population.crossover();//3  &  4
            population.mutate(); // 5
            population.calculateChildFitness(); //6
            population.replacement(); //7

            System.out.println("DEBUG:" + population.getBestChromosome());
        });
        return population.getBestChromosome();
    }
}
