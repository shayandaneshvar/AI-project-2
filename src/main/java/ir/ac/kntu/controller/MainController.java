package ir.ac.kntu.controller;

import ir.ac.kntu.view.View;

public class MainController implements Runnable {



    public void init() {
        run();
    }

    @Override
    public void run() {
        String question = View.getCommand();
    }
}
