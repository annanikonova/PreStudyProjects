package com.nixsolutions.project7.simple;

import interfaces.task7.simple.NamePrinterRunnable;
import interfaces.task7.simple.NamePrinterThread;

import java.io.*;

public class DemoTaskA {

    public static void main(String[] args) {

        NamePrinterRunnable runnable = new NamePrinterRunnableImpl();
        NamePrinterThread thread = new NamePrinterThreadChild();

        runnable.setPrintName("A - Runnable");
        runnable.setCount(6);
        runnable.setInterval(200);
        try {
            PrintStream stream = new PrintStream(new File("runnableLog.txt"));
            runnable.setStream(stream);
        } catch (IOException e) {
            System.out.println("Cannot create print stream: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Cannot set null stream: " + e.getMessage());
        }

        thread.setPrintName("B - Thread");
        thread.setInterval(300);
        thread.setCount(20);
        thread.setStream(System.err);

        new Thread(new NamePrinterRunnableImpl()).start(); //with default values
        new NamePrinterThreadChild().start(); // with default values
        thread.start(); //in error stream
        new Thread(runnable).start(); //in file stream



    }

}

