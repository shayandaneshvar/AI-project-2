package ir.ac.kntu.model.algorithms;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Algorithm<OUT> extends Callable<OUT> {

    @Override
    OUT call();

    default OUT callWithRetry(){
        try {
            return call();
        }catch (Exception e){
            e.printStackTrace();
            return callWithRetry();
        }
    }
}
