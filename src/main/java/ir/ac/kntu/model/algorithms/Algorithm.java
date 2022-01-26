package ir.ac.kntu.model.algorithms;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Algorithm extends Callable<String> {

    @Override
    String call();
}
