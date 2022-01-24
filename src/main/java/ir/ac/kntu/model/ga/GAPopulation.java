package ir.ac.kntu.model.ga;

import ir.ac.kntu.model.Population;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class GAPopulation extends Population<GAChromosome> {

    public GAPopulation(int populationSize, String text) {
        super(populationSize, text);
    }

    @Override
    protected List<GAChromosome> selectCandidateParents() {
        Collections.sort(super.chromosomes);
        return super.chromosomes.subList(0, 2 + super.populationSize / 10);
    }

    @Override
    protected void crossover() {
        var candidates = selectCandidateParents();
        for(int i = 0; i < candidates.size(); i++){
            for (int j = i + 1; j < candidates.size(); j++) {
                BitSet chromosomeI = candidates.get(i).getChromosome();
                BitSet chromosomeJ = candidates.get(j).getChromosome();
                BitSet child1 = new BitSet();
                BitSet child2 = new BitSet();
                for (int k = 0; k < chromosomeI.length(); k++) {
                    child1.set(k, k < chromosomeI.length()/3 ? chromosomeI.get(k) : chromosomeJ.get(k));
                    child2.set(k, k >= chromosomeI.length()/3 ? chromosomeI.get(k) : chromosomeJ.get(k));
                }
                super.childChromosomes.add(new GAChromosome(candidates.get(i), child1));
                super.childChromosomes.add(new GAChromosome(candidates.get(j), child2));
            }
        }
    }


    @Override
    protected void mutate() {
        childChromosomes.forEach(GAChromosome::mutate);
    }
}
