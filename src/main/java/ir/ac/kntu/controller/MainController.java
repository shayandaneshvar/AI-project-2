package ir.ac.kntu.controller;

import ir.ac.kntu.model.algorithms.genetic.ga.GeneticAlgorithm;
import ir.ac.kntu.model.algorithms.genetic.gp.GeneticProgrammingAlgorithm;
import ir.ac.kntu.model.algorithms.genetic.nsga2.Nsga2Algorithm;
import ir.ac.kntu.model.algorithms.greedy.GreedyAlgorithm;
import ir.ac.kntu.view.View;

public class MainController implements Runnable {

    public void init() {
        View.print("Welcome!");
        while (true) {
            run();
            View.print("Exit? [y]es or [n]o ?");
            if (View.getCommand().contains("y")) {
                break;
            }
            View.print("Running Again...");
        }
        View.print("Exiting...");
    }

    @Override
    public void run() {
        int algorithmId = View.chooseAlgorithm();
        int summaryLimit = View.getSummaryLimit();
        int populationSize;
        View.print("Enter Text: (after you wrote the text, press enter and write END_TEXT)");
        String text = View.getText();
        switch (algorithmId) {
            case 0:
                GreedyAlgorithm algorithm = new GreedyAlgorithm(summaryLimit, text);
                View.print("Final Result: \n" + algorithm.call());
                break;
            case 1:
                populationSize = View.getPopulationSize();
                GeneticAlgorithm ga = new GeneticAlgorithm(summaryLimit, populationSize, text);
                View.print("Running Algorithm...");
                View.print("Final Result: \n" + ga.call());
                break;
            case 2:
                populationSize = View.getPopulationSize();
                GeneticProgrammingAlgorithm gp = new GeneticProgrammingAlgorithm(summaryLimit, populationSize, text);
                View.print("Running Algorithm...");
                View.print("Final Result: \n" + gp.call());
                break;
            case 3:
                populationSize = View.getPopulationSize();
                var nsga2 = new Nsga2Algorithm(summaryLimit, populationSize, text);
                View.print("Final Result:\n" + nsga2.call().stream().map(z ->
                        "\n An output with Fitness 1 = " + z.getFirstFitness() +
                                " and Fitness 2 = " + z.getSecondFitness() + " :\n"
                                + z.getEquivalentText()).reduce((x, y) -> x + y)
                        .orElse(" "));
                break;
        }
    }
}
