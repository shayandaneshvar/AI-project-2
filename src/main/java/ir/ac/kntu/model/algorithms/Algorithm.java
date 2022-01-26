package ir.ac.kntu.model.algorithms;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Algorithm<OUT> extends Callable<OUT> {

    @Override
    OUT call();
}
