package ir.ac.kntu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TextReader implements AutoCloseable {
    private final Scanner scanner;

    public TextReader(String filename) {
        try {
            scanner = new Scanner(new File(getClass().getClassLoader().getResource(filename).getFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    public boolean endOfFile() {
        return scanner.hasNextLine();
    }
}
