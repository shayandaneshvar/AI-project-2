package ir.ac.kntu.util;

import java.util.concurrent.ThreadLocalRandom;

public final class Util {
    private Util() {
        throw new RuntimeException();
    }

    public static boolean action(double probability) {
        return ThreadLocalRandom.current().nextDouble(0, 1) <= probability;
    }


    public static String[] getSentences(String text) {
        text = text.replace(".", ";")
                .replace("!", ";")
                .replace("?", ";");
        return text.split(";");
    }
}
