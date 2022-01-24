package ir.ac.kntu.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

public enum ScannerWrapper implements Closeable {
    INSTANCE;
    private final Scanner scanner;

    ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public static String readLine() {
        return INSTANCE.scanner.nextLine();
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    public static boolean hasNextLine() {
        return INSTANCE.scanner.hasNextLine();
    }

}
