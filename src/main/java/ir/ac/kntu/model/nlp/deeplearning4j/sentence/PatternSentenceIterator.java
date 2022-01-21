package ir.ac.kntu.model.nlp.deeplearning4j.sentence;

import com.google.common.io.CharStreams;
import org.deeplearning4j.text.sentenceiterator.BaseSentenceIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternSentenceIterator extends BaseSentenceIterator {

  private final Pattern pattern;
  private final Charset charset;
  private final Set<Path> pathSet;
  private final Deque<Path> pathDeque;
  private Matcher currentMatcher = null;
  private String currentMatch = null;

  public PatternSentenceIterator(
           Pattern pattern,  Charset charset,  Collection<Path> paths) {
    super();
    this.pattern = pattern;
    this.charset = charset;
    this.pathSet = new HashSet<>(paths);
    this.pathDeque = new ArrayDeque<>(pathSet);
    advance();
  }

  private void advance() {
    if (currentMatcher == null) {
      if (pathDeque.isEmpty()) {
        currentMatch = null;
        return;
      }
      nextMatcher();
    }
    if (currentMatcher.find()) {
      currentMatch = currentMatcher.group();
      return;
    }
    currentMatcher = null;
    advance();
  }

  private void nextMatcher() {
    Path path = Objects.requireNonNull(pathDeque.poll());
    try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
      //noinspection UnstableApiUsage
      currentMatcher = pattern.matcher(CharStreams.toString(reader));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String nextSentence() {
    if (currentMatch == null) {
      throw new NoSuchElementException();
    }
    String m = currentMatch;
    advance();
    return m;
  }

  @Override
  public boolean hasNext() {
    return currentMatch != null;
  }

  @Override
  public void reset() {
    currentMatcher = null;
    pathDeque.clear();
    pathDeque.addAll(pathSet);
    advance();
  }
}
