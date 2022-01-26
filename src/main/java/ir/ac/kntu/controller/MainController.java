package ir.ac.kntu.controller;

import ir.ac.kntu.model.Algorithm;
import ir.ac.kntu.model.genetic.ga.GeneticAlgorithm;
import ir.ac.kntu.model.genetic.gp.GeneticProgrammingAlgorithm;
import ir.ac.kntu.model.greedy.GreedyAlgorithm;
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
        Algorithm algorithm = null;
        switch (algorithmId) {
            case 0:
                algorithm = new GreedyAlgorithm(summaryLimit, text);
                break;
            case 1:
                populationSize = View.getPopulationSize();
                algorithm = new GeneticAlgorithm(summaryLimit, populationSize, text);
                break;
            case 2:
                populationSize = View.getPopulationSize();
                algorithm = new GeneticProgrammingAlgorithm(summaryLimit, populationSize, text);
                break;
            case 3:
                // TODO: 1/25/2022
        }
        View.print("Running Algorithm...");
        View.print("Final Result: \n" + algorithm.call());
    }
}
