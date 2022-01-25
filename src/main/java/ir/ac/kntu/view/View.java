package ir.ac.kntu.view;

import ir.ac.kntu.util.ScannerWrapper;

public final class View {


    private View() {
        throw new RuntimeException();
    }

    public static String getText() {
        StringBuilder text = new StringBuilder("");
        while (ScannerWrapper.hasNextLine()) {
            text.append(ScannerWrapper.readLine());
        }
        return text.toString();
    }

    public static String getCommand() {
        return ScannerWrapper.readLine();
    }

    public static int getSummaryLimit() {
        print("Enter Summary Limit:");
        try {
            return Integer.parseInt(getCommand());

        } catch (Exception ignore) {
            print("Invalid!");
            return getSummaryLimit();
        }
    }

    public static int chooseAlgorithm() {
        try {
            print("Choose Solving Algorithm:");
            print("1- Genetic Algorithm");
            print("2- Genetic Programming");
            print("3- Multi-Objective Particle Swarm Optimization");
            int result = Integer.parseInt(getCommand());
            if (result < 1 || result > 3) {
                throw new RuntimeException();
            }
            return result;
        } catch (Exception ignore) {
            print("Wrong Choice!");
            return chooseAlgorithm();
        }
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static int getPopulationSize() {
        try {
            print("Enter Initial Population:");
            int populationSize = Integer.parseInt(getCommand());
            if (populationSize < 0) {
                throw new RuntimeException();
            }
            return populationSize;
        } catch (Exception e) {
            return getPopulationSize();
        }
    }
}
