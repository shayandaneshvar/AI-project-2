package ir.ac.kntu.model.algorithms.genetic.nsga2;

import ir.ac.kntu.model.algorithms.genetic.Chromosome;
import ir.ac.kntu.model.algorithms.genetic.ga.GAChromosome;
import ir.ac.kntu.model.datastructures.BitSet;

import java.util.Objects;
import java.util.UUID;

public class Nsga2Chromosome extends GAChromosome {
    private double secondFitness;
    private final String uuid;

    public Nsga2Chromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        super(sentences, sentenceScores, similarities);
        uuid = UUID.randomUUID().toString();
    }

    public Nsga2Chromosome(GAChromosome gaChromosome, BitSet chromosome) {
        super(gaChromosome, chromosome);
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nsga2Chromosome)) return false;
        Nsga2Chromosome that = (Nsga2Chromosome) o;
        return that.uuid.equals(uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public double getSecondFitness() {
        return secondFitness;
    }

    public double getFirstFitness() {
        return getFitness();
    }

    @Override
    public void calculateFitness() {
        double scoreSum = 0;
        double similaritySum = 0;
        for (int i = 0; i < super.getChromosome().length(); i++) {
            scoreSum += super.getChromosome().get(i) ? super.sentenceScores[i] : 0;
            for (int j = 0; j < sentences.length; j++) {
                if (i == j || !super.getChromosome().get(j)) {
                    continue;
                }
                similaritySum += similarities[i][j];
            }
        }
        fitness = alpha * scoreSum;
        secondFitness = beta * (1 / (similaritySum + 1));
    }

    @Override
    public int compareTo(Chromosome o) {
        throw new RuntimeException("Use Specific Comparators!");
    }

    public boolean dominates(Nsga2Chromosome other) {
        return (this.getFirstFitness() <= other.getFirstFitness() && this.secondFitness <= other.getSecondFitness()) &&
                (this.getFirstFitness() < other.getFirstFitness() || this.secondFitness < other.getSecondFitness());
    }
}
