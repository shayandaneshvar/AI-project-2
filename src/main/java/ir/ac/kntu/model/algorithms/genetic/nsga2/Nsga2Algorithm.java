package ir.ac.kntu.model.algorithms.genetic.nsga2;

import ir.ac.kntu.model.algorithms.Algorithm;
import ir.ac.kntu.model.algorithms.genetic.BaseGeneticAlgorithm;
import ir.ac.kntu.model.algorithms.genetic.Population;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class Nsga2Algorithm implements Algorithm<List<Nsga2Chromosome>> {
    private final Nsga2Population population;
    private static final int maxGenerations = 20;

    public Nsga2Algorithm(int summaryLimit, int populationSize, String text) {
        population = new Nsga2Population(summaryLimit, populationSize, text);
    }

    @Override
    public List<Nsga2Chromosome> call() {
        long start = System.currentTimeMillis();
        population.generateInitialPopulation();// 1
        IntStream.range(0, maxGenerations).forEach(z -> {
            System.out.println("INFO: Generation " + (z + 1));
            population.calculateParentFitness(); // 2
            population.crossover();//3  &  4
            population.mutate(); // 5
            population.calculateChildFitness(); //6
            population.replacement(); //7
            System.out.println("DEBUG:" + population.getCurrentPopulationSize());
            }
        );
        long time = System.currentTimeMillis() - start;
        System.out.println("INFO: time passed running the algorithm is " + time + " ms");
        return population.getChromosomes();
    }
}
