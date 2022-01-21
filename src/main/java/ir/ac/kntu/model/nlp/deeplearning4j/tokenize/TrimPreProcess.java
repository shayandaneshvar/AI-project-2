package ir.ac.kntu.model.nlp.deeplearning4j.tokenize;

import org.apache.commons.lang3.StringUtils;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;

public final class TrimPreProcess implements TokenPreProcess {

  @Override
  public String preProcess(String s) {
    return StringUtils.trimToNull(s);
  }
}
