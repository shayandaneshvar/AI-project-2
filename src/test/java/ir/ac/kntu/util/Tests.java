package ir.ac.kntu.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Tests {
    @Test
    public void test() throws IOException {
        TextReader textReader = new TextReader(Utils.WORDS_ADDRESS);
        System.out.println(textReader.nextLine());
        System.out.println(textReader.nextLine());
        textReader.close();
    }
}
