package ir.ac.kntu.util;

public final class Util {
    private Util() {
        throw new RuntimeException();
    }

    public static String[] getSentences(String text) {
        text = text.replace(".", ";")
                .replace("!", ";")
                .replace("?", ";");
        return text.split(";");
    }
}
