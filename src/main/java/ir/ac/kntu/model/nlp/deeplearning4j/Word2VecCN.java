package ir.ac.kntu.model.nlp.deeplearning4j;

import com.github.yuyu.sentence.PatternSentenceIterator;
import com.github.yuyu.tokenize.AnsjTokenizerFactory;
import com.github.yuyu.tokenize.TrimPreProcess;
import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public final class Word2VecCN {

    private final int minWordFrequency;
    private final int iterations;
    private final int layerSize;
    private final long seed = ThreadLocalRandom.current().nextLong();
    private final int windowSize;

    private final TokenizerFactory tokenizerFactory;
    private final SentenceIterator sentenceIterator;

    private Word2VecCN(int minWordFrequency, int iterations, int layerSize, int windowSize) {
        this.minWordFrequency = minWordFrequency;
        this.iterations = iterations;
        this.layerSize = layerSize;
        this.windowSize = windowSize;
    }

    public static Word2VecCNBuilder builder() {
        return new Word2VecCNBuilder();
    }

    public Word2Vec fit() {
        Word2Vec vec =
                new Word2Vec.Builder()
                        .minWordFrequency(minWordFrequency)
                        .iterations(iterations)
                        .layerSize(layerSize)
                        .seed(seed)
                        .windowSize(windowSize)
                        .iterate(sentenceIterator)
                        .tokenizerFactory(tokenizerFactory)
                        .build();

        vec.fit();
        return vec;
    }

    public static final class Word2VecCNBuilder {

        private final LinkedHashSet<Path> files = new LinkedHashSet<>();
        private int minWordFrequency = 5;
        private int iterations = 1;
        private int layerSize = 100;
        private int windowSize = 5;
        private TokenizerFactory tokenizerFactory = new AnsjTokenizerFactory(new TrimPreProcess());
        private Charset charset = StandardCharsets.UTF_8;

        private Pattern sentencePatten = Pattern.compile("[\u4e00-\u9fa50-9a-zA-Z\r\n]+");

        private static void ensurePositive(int i) {
            Preconditions.checkArgument(i > 0, "a positive number is required, but found: %s", i);
        }

        public Word2VecCNBuilder minWordFrequency(int minWordFrequency) {
            ensurePositive(minWordFrequency);
            this.minWordFrequency = minWordFrequency;
            return this;
        }

        public Word2VecCNBuilder iterations(int iterations) {
            ensurePositive(iterations);
            this.iterations = iterations;
            return this;
        }

        public Word2VecCNBuilder layerSize(int layerSize) {
            ensurePositive(layerSize);
            this.layerSize = layerSize;
            return this;
        }

        public Word2VecCNBuilder windowSize(int windowSize) {
            ensurePositive(windowSize);
            this.windowSize = windowSize;
            return this;
        }

        public Word2VecCNBuilder tokenizerFactory(TokenizerFactory tokenizerFactory) {
            this.tokenizerFactory = tokenizerFactory;
            return this;
        }

        private Word2VecCNBuilder addFile(File file) {
            return addFile(file.toPath());
        }

        public Word2VecCNBuilder addFile(Path path) {
            Preconditions.checkArgument(
                    Files.isRegularFile(path)
                            && Files.isReadable(path), "invalid file path: %s",
                    path
            );
            if (!files.add(path)) {
                System.out.println(("file [{" + path + "}] already added!");
            } else {
                System.err.println("file [{" + path + "}] added successfully!");
            }
            return this;
        }

        public Word2VecCNBuilder addFile( String path) {
            return addFile(Paths.get(path));
        }

        public Word2VecCNBuilder addAllFiles( Path path,  Predicate<? super Path> predicate) {
            Preconditions.checkArgument(
                    Files.isDirectory(path)
                            && Files.isReadable(path),
                    "invalid directory: %s", path
            );
            try (Stream<Path> pathStream = Files.walk(path)) {
                pathStream
                        .filter(predicate)
                        .forEach(this::addFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Word2VecCNBuilder addAllFiles( String path,  Predicate<? super String> predicate) {
            return addAllFiles(Paths.get(path), (Path p) -> predicate.test(p.toString()));
        }

        public Word2VecCNBuilder addAllFiles( File file,  Predicate<? super File> predicate) {
            return addAllFiles(file.toPath(), (Path p) -> predicate.test(new File(p.toString())));
        }

        public Word2VecCNBuilder charset( Charset charset) {
            this.charset = charset;
            return this;
        }

        public Word2VecCNBuilder sentencePattern( String pattern) {
            this.sentencePatten = Pattern.compile(pattern);
            return this;
        }

        public Word2VecCN build() {
            return new Word2VecCN(
                    minWordFrequency,
                    iterations,
                    layerSize,
                    windowSize,
                    tokenizerFactory,
                    new PatternSentenceIterator(sentencePatten, charset, files));
        }
    }
}
