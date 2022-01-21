package ir.ac.kntu.view;

import ir.ac.kntu.util.ScannerWrapper;

public final class View {

    private View() {
        throw new RuntimeException();
    }

    public static String getCommand() {
        return ScannerWrapper.readLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }
}
