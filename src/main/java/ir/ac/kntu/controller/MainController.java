package ir.ac.kntu.controller;

import ir.ac.kntu.model.Algorithm;
import ir.ac.kntu.model.genetic.ga.GeneticAlgorithm;
import ir.ac.kntu.model.genetic.gp.GeneticProgrammingAlgorithm;
import ir.ac.kntu.view.View;

public class MainController implements Runnable {

    public void init() {
        run();
    }

    @Override
    public void run() {
        View.print("Welcome!");
        int algorithmId = View.chooseAlgorithm();
        int summaryLimit = View.getSummaryLimit();
        int populationSize = View.getPopulationSize();
        View.print("Enter Text:");
        String text = View.getText();
        Algorithm algorithm = null;
        switch (algorithmId) {
            case 1:
                algorithm = new GeneticAlgorithm(summaryLimit, populationSize, text);
                break;
            case 2:
                algorithm = new GeneticProgrammingAlgorithm(summaryLimit, populationSize, text);
                break;
            case 3:
                // TODO: 1/25/2022
        }
        View.print("Running Algorithm...");
        View.print(algorithm.call());
    }
}
