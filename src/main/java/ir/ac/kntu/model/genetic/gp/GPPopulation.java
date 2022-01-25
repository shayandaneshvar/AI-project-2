package ir.ac.kntu.model.genetic.gp;

import ir.ac.kntu.model.genetic.Population;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class GPPopulation extends Population<GPChromosome> {

    public GPPopulation(int summaryLimit, int populationSize, String text) {
        super(summaryLimit, populationSize, text);
    }

    public GPChromosome newChromosome(String[] sentences, double[] sentenceScores, double[][] similarities) {
        return new GPChromosome(sentences, sentenceScores, similarities);
    }


    @Override
    protected void crossover() {
        var candidates = selectCandidateParents();
        for (int i = 0; i < candidates.size(); i++) {
            for (int j = i + 1; j < candidates.size(); j++) {
                TreeSet<Integer> chromosomeI = candidates.get(i).getChromosome();
                TreeSet<Integer> chromosomeJ = candidates.get(j).getChromosome();

                TreeSet<Integer> child1 = new TreeSet<>();
                TreeSet<Integer> child2 = new TreeSet<>();

                List<Integer> intersection = chromosomeI.stream().filter(chromosomeJ::contains).sorted()
                        .collect(Collectors.toList());
                Integer middleElement = intersection.get(intersection.size() / 2);

                child1.addAll(chromosomeI.headSet(middleElement));
                child1.addAll(chromosomeJ.tailSet(middleElement));

                child2.addAll(chromosomeI.tailSet(middleElement));
                child2.addAll(chromosomeJ.headSet(middleElement));

                super.childChromosomes.add(new GPChromosome(candidates.get(i), child1));
                super.childChromosomes.add(new GPChromosome(candidates.get(j), child2));
            }
        }
    }


}
