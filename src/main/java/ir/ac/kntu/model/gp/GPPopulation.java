package ir.ac.kntu.model.gp;

import ir.ac.kntu.model.Population;
import ir.ac.kntu.model.ga.GAChromosome;

import java.util.BitSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class GPPopulation extends Population<GPChromosome> {

    public GPPopulation(int populationSize, String text) {
        super(populationSize, text);
    }



    @Override
    protected void crossover() {
        var candidates = selectCandidateParents();
        for(int i = 0; i < candidates.size(); i++){
            for (int j = i + 1; j < candidates.size(); j++) {
                TreeSet<Integer> chromosomeI = candidates.get(i).getChromosome();
                TreeSet<Integer> chromosomeJ = candidates.get(j).getChromosome();

                TreeSet<Integer> child1 = new TreeSet<>();
                TreeSet<Integer> child2 = new TreeSet<>();

                List<Integer> intersection = chromosomeI.stream().filter(chromosomeJ::contains).sorted()
                        .collect(Collectors.toList());
                Integer middleElement = intersection.get(intersection.size()/2);

                child1.addAll(chromosomeI.headSet(middleElement));
                child1.addAll(chromosomeJ.tailSet(middleElement));

                // I : 1 3 5 8 9
                // J : 2 6 7 8 10

                child2.addAll(chromosomeI.tailSet(middleElement));
                child2.addAll(chromosomeJ.headSet(middleElement));




                super.childChromosomes.add(new GPChromosome(candidates.get(i), child1));
                super.childChromosomes.add(new GPChromosome(candidates.get(j), child2));
            }
        }
    }


//    @Override
//    protected void crossover() {
//        var candidates = selectCandidateParents();
//        for(int i = 0; i < candidates.size(); i++){
//            for (int j = i + 1; j < candidates.size(); j++) {
//                BitSet chromosomeI = candidates.get(i).getChromosome();
//                BitSet chromosomeJ = candidates.get(j).getChromosome();
//                BitSet child1 = new BitSet();
//                BitSet child2 = new BitSet();
//                for (int k = 0; k < chromosomeI.length(); k++) {
//                    child1.set(k, k < chromosomeI.length()/3 ? chromosomeI.get(k) : chromosomeJ.get(k));
//                    child2.set(k, k >= chromosomeI.length()/3 ? chromosomeI.get(k) : chromosomeJ.get(k));
//                }
//                super.childChromosomes.add(new GAChromosome(candidates.get(i), child1));
//                super.childChromosomes.add(new GAChromosome(candidates.get(j), child2));
//            }
//        }
//    }
}
