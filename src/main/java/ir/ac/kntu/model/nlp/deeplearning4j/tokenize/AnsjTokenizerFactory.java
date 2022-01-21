package ir.ac.kntu.model.nlp.deeplearning4j.tokenize;

import lombok.NonNull;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.InputStream;

public final class AnsjTokenizerFactory implements TokenizerFactory {

  private final TokenPreProcess tokenPreProcess;

  public AnsjTokenizerFactory( TokenPreProcess tokenPreProcess) {
    this.tokenPreProcess = tokenPreProcess;
  }

  @Override
  public Tokenizer create(String s) {
    return new AnsjTokenizer(s, tokenPreProcess);
  }

  @Override
  public Tokenizer create(InputStream inputStream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public TokenPreProcess getTokenPreProcessor() {
    return tokenPreProcess;
  }

  @Override
  public void setTokenPreProcessor(TokenPreProcess tokenPreProcess) {
    throw new UnsupportedOperationException();
  }
}
