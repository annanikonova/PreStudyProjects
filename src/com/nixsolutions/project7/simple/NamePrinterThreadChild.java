package com.nixsolutions.project7.simple;

import interfaces.task7.simple.NamePrinterThread;

import java.io.PrintStream;

/**
 * Created by annnikon on 03.02.17.
 */
public class NamePrinterThreadChild extends NamePrinterThread {
    private static final String DEFAULT_NAME = "Default thread printer";
    private NamePrinterRunnableImpl runnable;

    public NamePrinterThreadChild() {
        this.runnable=new NamePrinterRunnableImpl();
        runnable.setPrintName(DEFAULT_NAME);
    }

    @Override
    public void run() {
        runnable.run();
    }

    public void setPrintName(String name) {
        runnable.setPrintName(name);
    }

    public void setStream(PrintStream printStream) {
        runnable.setStream(printStream);
    }


    public void setInterval(long interval) {
        runnable.setInterval(interval);
    }


    public void setCount(int count) {
        runnable.setCount(count);

    }
}
