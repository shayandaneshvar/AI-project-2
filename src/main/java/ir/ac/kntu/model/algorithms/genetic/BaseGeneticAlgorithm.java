package ir.ac.kntu.model.algorithms.genetic;

import ir.ac.kntu.model.algorithms.Algorithm;

import java.util.concurrent.atomic.AtomicReference;
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
        AtomicReference<Double> bestFitness = new AtomicReference<>(-1d);
        AtomicReference<String> bestText = new AtomicReference<>();
        long start = System.currentTimeMillis();
        population.generateInitialPopulation();// 1
        IntStream.range(0, maxGenerations).forEach(z -> {
            System.out.println("INFO: Generation " + (z + 1));
            population.calculateParentFitness(); // 2
            population.crossover();//3  &  4
            population.mutate(); // 5
            population.calculateChildFitness(); //6
            population.replacement(); //7
//            System.out.println("DEBUG:" + population.getBestChromosome());
            System.out.println("DEBUG:" + population.getBestFitness());
            System.out.println("DEBUG:" + population.getCurrentPopulationSize());
            if (bestFitness.get() < population.getBestFitness()) {
                bestFitness.set(population.getBestFitness());
                bestText.set(population.getBestChromosome());
            }
        });
        long time = System.currentTimeMillis() - start;
        System.out.println("INFO: time passed running the algorithm is " + time + " ms");
        System.out.println("DEBUG: best fitness achieved is " + bestFitness.get());
        return bestText.get();
    }
}
