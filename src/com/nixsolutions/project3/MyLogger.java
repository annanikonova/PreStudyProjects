package com.nixsolutions.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Performs exception logging into output and error streams.
 * Suitable for all types of Exception.
 * Created by annnikon on 26.01.17.
 */
public class MyLogger {
    public static final String PRINT_SEPARATOR = "------------------";

    static {
        try {
            /*Error stream will be redirected into file. */
            System.setErr(new PrintStream(new File("log.txt")));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows a reason of eception in output stream.
     * Writes full stacktrace for exception in error stream.
     */
    public static void log(Exception ex) {
        System.out.println("Exception handled: " + ex.getMessage()); // into  console
        /*Details - into file*/
        System.err.println("Got an exception: " + ex.getMessage());

        if (ex.getCause()!=null){
            System.err.println("Caused by: "+ ex.getCause());
        }
        System.err.println("with a stacktrace: ");
        StackTraceElement elements[] = ex.getStackTrace();
        for (StackTraceElement element : elements) {
            System.err.println("\tError in file: " + element.getFileName());
            System.err.println("\tin method: " + element.getMethodName());
            System.err.println("\tline: " + element.getLineNumber());
            System.err.println();
        }
        System.err.println(PRINT_SEPARATOR);
    }

}
