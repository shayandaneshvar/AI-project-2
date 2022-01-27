package ir.ac.kntu.model.algorithms.genetic.nsga2;

import ir.ac.kntu.model.algorithms.genetic.Population;
import ir.ac.kntu.model.algorithms.genetic.ga.GAChromosome;
import ir.ac.kntu.model.datastructures.BitSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Nsga2Population extends Population<Nsga2Chromosome> {
    public Nsga2Population(int summaryLimit, int populationSize, String text) {
        super(summaryLimit, populationSize, text);
    }

    @Override
    public Nsga2Chromosome newChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        return new Nsga2Chromosome(sentences, sentenceScores, similarities);
    }

    @Override
    protected void crossover() {
        var candidates = chromosomes.stream()
                .filter(t -> t.getEquivalentText().trim().split(" ").length <= super.getSummaryLimit())
                .collect(Collectors.toList());
        for (int i = 0; i < candidates.size(); i += 2) {
            if (i + 1 == candidates.size()) {
                break;
            }
            BitSet chromosomeI = candidates.get(i).getChromosome();
            BitSet chromosomeJ = candidates.get(i + 1).getChromosome();
            BitSet child1 = new BitSet(chromosomeI.length());
            BitSet child2 = new BitSet(chromosomeI.length());
            for (int k = 0; k < chromosomeI.length(); k++) {
                child1.set(k, k < chromosomeI.length() / 3 ? chromosomeI.get(k) : chromosomeJ.get(k));
                child2.set(k, k >= chromosomeI.length() / 3 ? chromosomeI.get(k) : chromosomeJ.get(k));
            }
            super.childChromosomes.add(new Nsga2Chromosome(candidates.get(i), child1));
            super.childChromosomes.add(new Nsga2Chromosome(candidates.get(i + 1), child2));
        }
    }

    @Override
    public String getBestChromosome() {
        throw new RuntimeException("NO BEST CHROMOSOME FOR NSGA2");
    }

    @Override
    public double getBestFitness() {
        throw new RuntimeException("NO BEST CHROMOSOME FOR NSGA2");
    }

    @Override
    protected void replacement() {
        chromosomes.addAll(childChromosomes);
        childChromosomes = new ArrayList<>();
        List<Nsga2Chromosome> nextGenerationChromosomes = new ArrayList<>();
        var frontiers = nonDominatedSorting();
        var lastUnfitFrontier = firstFrontiersSelection(frontiers, nextGenerationChromosomes);
        crowdDistanceSorting(lastUnfitFrontier, nextGenerationChromosomes);
        chromosomes = nextGenerationChromosomes;
    }

    private void crowdDistanceSorting(LinkedList<Nsga2Chromosome> frontier,
                                      List<Nsga2Chromosome> nextGenerationChromosomes) {
        final double INF = 1e22;
        if (frontier == null || frontier.size() == 0) {
            return;
        }
        HashMap<Nsga2Chromosome, Double> distances = new HashMap<>();
        frontier.forEach(z -> distances.put(z, 0D));
        var I = frontier.stream()
                .sorted((x, y) -> (int) (1e10 * (-x.getFirstFitness() +
                        y.getFirstFitness()))).collect(Collectors.toList());
        distances.put(I.get(0), INF);
        distances.put(I.get(I.size() - 1), INF);
        double f1Max = sentences.length * 16;
        double f1Min = 5;
        double f2Max = 1;
        double f2Min = 0.5;
        for (int i = 1; i < I.size() - 1; i++) {
            var current = I.get(i);
            distances.put(current, distances.get(current) +
                    (I.get(i - 1).getFirstFitness() + I.get(i + 1).getFirstFitness()) / (f1Max - f1Min));
        }
        // second iteration
        I = frontier.stream()
                .sorted((x, y) -> (int) (1e10 * (-x.getSecondFitness() +
                        y.getSecondFitness()))).collect(Collectors.toList());
        distances.put(I.get(0), INF);
        distances.put(I.get(I.size() - 1), INF);
        for (int i = 1; i < I.size() - 1; i++) {
            var current = I.get(i);
            distances.put(current, distances.get(current) +
                    (I.get(i - 1).getSecondFitness() + I.get(i + 1).getSecondFitness()) / (f2Max - f2Min));
        }

        while (nextGenerationChromosomes.size() < populationSize) {
            double maxDistance = 0;
            Nsga2Chromosome maxDistanceChromosome = null;
            for (Nsga2Chromosome current : distances.keySet()) {
                if (distances.get(current) > maxDistance) {
                    maxDistance = distances.get(current);
                    maxDistanceChromosome = current;
                }
            }
            distances.remove(maxDistanceChromosome);
            nextGenerationChromosomes.add(maxDistanceChromosome);
        }
    }

    private LinkedList<Nsga2Chromosome> firstFrontiersSelection(HashMap<Integer,
            LinkedList<Nsga2Chromosome>> frontiers, List<Nsga2Chromosome> nextGenerationChromosomes) {
        for (int i = 1; i <= frontiers.size(); i++) {
            var frontier = frontiers.get(i);
            if (frontier.size() + nextGenerationChromosomes.size() <= populationSize) {
                nextGenerationChromosomes.addAll(frontier);
            } else {
                return frontier;
            }
        }
        return null;
    }

    private HashMap<Integer, LinkedList<Nsga2Chromosome>> nonDominatedSorting() {
        HashMap<Integer, LinkedList<Nsga2Chromosome>> result = new HashMap<>();
        HashMap<Nsga2Chromosome, List<Nsga2Chromosome>> dominatingChromosomes = new HashMap<>();
        HashMap<Nsga2Chromosome, Integer> dominatingChromosomeCount = new HashMap<>();
        // first frontier
        for (Nsga2Chromosome chromosome : chromosomes) {
            dominatingChromosomeCount.put(chromosome, 0);
            dominatingChromosomes.put(chromosome, new ArrayList<>());
            for (Nsga2Chromosome chromosomeQ : chromosomes) {
                if (chromosome.dominates(chromosomeQ)) {
                    dominatingChromosomes.get(chromosome).add(chromosomeQ);
                } else if (chromosomeQ.dominates(chromosome)) {
                    Integer count = dominatingChromosomeCount.get(chromosome);
                    dominatingChromosomeCount.put(chromosome, count + 1);
                }
            }
            if (dominatingChromosomeCount.get(chromosome) == 0) {
                LinkedList<Nsga2Chromosome> f1 = result
                        .computeIfAbsent(1, k -> new LinkedList<>());
                f1.push(chromosome);
            }
        }
        // other frontiers
        while (true) {
            LinkedList<Nsga2Chromosome> temp = new LinkedList<>();
            for (Nsga2Chromosome chromosome : result.get(result.size())) {
                for (Nsga2Chromosome q : dominatingChromosomes.get(chromosome)) {
                    int count = dominatingChromosomeCount.get(q) - 1;
                    dominatingChromosomeCount.put(q, count);
                    if (count == 0) {
                        temp.add(q);
                    }
                }
            }
            if (temp.isEmpty()) {
                break;
            }
            result.put(result.size() + 1, temp);
        }
        return result;
    }

}
