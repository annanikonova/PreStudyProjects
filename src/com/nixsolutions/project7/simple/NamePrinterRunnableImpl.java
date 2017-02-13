package com.nixsolutions.project7.simple;

import interfaces.task7.simple.NamePrinter;
import interfaces.task7.simple.NamePrinterRunnable;
import interfaces.task7.simple.NamePrinterThread;

import java.io.PrintStream;

/**
 * Created by annnikon on 03.02.17.
 */
public class NamePrinterRunnableImpl implements NamePrinterRunnable {

    private static final String DEFAULT_NAME = "Default runnable printer";
    private static final int DEFAULT_COUNT = 10;
    private static final long DEFAULT_INTERVAL = 1000;
    private static final PrintStream DEFAULT_STREAM = System.out;

    private String name;
    private int count;
    private long interval;
    private PrintStream stream;

    public NamePrinterRunnableImpl() {

        setPrintName(DEFAULT_NAME);
        setCount(DEFAULT_COUNT);
        setInterval(DEFAULT_INTERVAL);
        setStream(DEFAULT_STREAM);
    }

    @Override
    public void setPrintName(String s) {
        if (s == null) {
            throw new NullPointerException("Null name given. ");
        }
        if (s.length()==0) {
            throw  new IllegalArgumentException("Empty name given. ");
        }
        this.name=s;
    }

    @Override
    public void setStream(PrintStream printStream) {
        if (printStream == null) {
            throw new NullPointerException("Null stream given. ");
        }
        this.stream = printStream;
    }

    @Override
    public void setInterval(long interval) {
        if (interval<=0) {
            throw  new IllegalArgumentException("Interval should be positive. ");
        }
        this.interval=interval;

    }

    @Override
    public void setCount(int count) {
        if (count<=0) {
            throw  new IllegalArgumentException("Count should be positive. ");
        }
        this.count=count;

    }

    @Override
    public void run() {
        try {
            for(int i=0; i<count; i++) {
               stream.print(i+1 + ": ");
               stream.println("My name is: "+ name);
               Thread.sleep(interval);
            }

        }
        catch (InterruptedException e) {
            stream.println("Thread interrupted: "+name);

        }

    }
}
