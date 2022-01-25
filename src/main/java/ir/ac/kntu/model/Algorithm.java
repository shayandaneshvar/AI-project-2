package ir.ac.kntu.model;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Algorithm extends Callable<String> {

    @Override
    String call();
}
