package ir.ac.kntu.view;

import ir.ac.kntu.util.ScannerWrapper;

public final class View {


    private View() {
        throw new RuntimeException();
    }

    public static String getText(){
        StringBuilder text = new StringBuilder("");
        while (ScannerWrapper.hasNextLine()){
            text.append(ScannerWrapper.readLine());
        }
        return text.toString();
    }

    public static String getCommand() {
        return ScannerWrapper.readLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }
}
