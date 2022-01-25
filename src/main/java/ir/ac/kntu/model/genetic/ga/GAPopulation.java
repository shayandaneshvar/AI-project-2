package ir.ac.kntu.model.genetic.ga;

import ir.ac.kntu.model.BitSet;
import ir.ac.kntu.model.genetic.Population;
import ir.ac.kntu.model.genetic.gp.GPChromosome;


public class GAPopulation extends Population<GAChromosome> {

    public GAPopulation(int summaryLimit,int populationSize, String text) {
        super(summaryLimit,populationSize, text);
    }

    public GAChromosome newChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        return new GAChromosome(sentences, sentenceScores, similarities);
    }

    @Override
    protected void crossover() {
        var candidates = selectCandidateParents();
        for (int i = 0; i < candidates.size(); i++) {
            for (int j = i + 1; j < candidates.size(); j++) {
                BitSet chromosomeI = candidates.get(i).getChromosome();
                BitSet chromosomeJ = candidates.get(j).getChromosome();
                BitSet child1 = new BitSet(chromosomeI.length());
                BitSet child2 = new BitSet(chromosomeI.length());
                for (int k = 0; k < chromosomeI.length(); k++) {
                    child1.set(k, k < chromosomeI.length() / 3 ? chromosomeI.get(k) : chromosomeJ.get(k));
                    child2.set(k, k >= chromosomeI.length() / 3 ? chromosomeI.get(k) : chromosomeJ.get(k));
                }
                super.childChromosomes.add(new GAChromosome(candidates.get(i), child1));
                super.childChromosomes.add(new GAChromosome(candidates.get(j), child2));
            }
        }
    }
}
