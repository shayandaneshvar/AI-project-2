package ir.ac.kntu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextReader {
    private final Scanner scanner;

    public TextReader(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String nextLine() {
        return scanner.nextLine();
    }
}
