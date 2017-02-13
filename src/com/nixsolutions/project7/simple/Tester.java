package com.nixsolutions.project7.simple;

import interfaces.task7.simple.NamePrinter;
import interfaces.task7.simple.NamePrinterRunnable;
import interfaces.task7.simple.NamePrinterThread;

import java.io.*;
import java.util.Scanner;

/**
 * Created by annnikon on 08.02.17.
 */
public class Tester {

    private static String FILE_NAME = "printerLog.txt";
    private static long PRINT_INTERVAL = 100;
    private static int PRINT_COUNT = 5;
    private  static NamePrinter printerRunnable, printerThread;
    private static Thread thread1, thread2;

    private static void interruptThread(Thread thread) throws Exception {

             do {
                 thread.interrupt();
                 Thread.sleep(1);

             } while (thread.isAlive());
        }


     public static void testCreate(){
         printerRunnable = new NamePrinterRunnableImpl();
         printerThread = new NamePrinterThreadChild();
         System.out.println("Created NamePrinterRunnable: "
                 +(printerRunnable instanceof NamePrinterRunnable));
         thread1 = new Thread((NamePrinterRunnable)printerRunnable);
         System.out.println("Created NamePrinterThread: "
                 +(printerThread instanceof NamePrinterThread));
         thread2 = (NamePrinterThread) printerThread;
         System.out.println("Thread 1 is not alive before start: "+!thread1.isAlive());
         System.out.println("Thread 2 is not alive before start: "+!thread2.isAlive());
         thread1.start();
         System.out.println("Thread 1 is alive after start: "+thread1.isAlive());
         try{
             System.out.println("Waiting for thread1 finish:");
             thread1.join();
            }
         catch(Exception ignored) {}
         System.out.println("Thread 1 is not alive after it finished: "+!thread1.isAlive());

         thread2.start();
         try{
             interruptThread(thread2);
         }
        catch(Exception ignored) {}
         System.out.println("Thread 2 is not alive after interruption: "+!thread1.isAlive());



     }

     public static void testSetName() {
         System.out.print("Set null name for runnable:");
         try {
             printerRunnable.setPrintName(null);

                      } catch (NullPointerException e) {
             System.out.println(e.getMessage());

                 }
         System.out.print("Set null name for thread:");
         try {
             printerThread.setPrintName(null);

         } catch (NullPointerException e) {
             System.out.println(e.getMessage());

         }
         System.out.print("Set empty name for runnable:");
         try {
             printerRunnable.setPrintName("");

         } catch (IllegalArgumentException e) {
             System.out.println(e.getMessage());

         }
         System.out.print("Set empty name for thread:");
         try {
             printerThread.setPrintName("");

         } catch (IllegalArgumentException e) {
             System.out.println(e.getMessage());

         }
     }

     public static void testSetStream() {
         System.out.print("Set null stream for runnable:");
         try {
             printerRunnable.setStream(null);

         } catch (NullPointerException e) {
             System.out.println(e.getMessage());

         }
         System.out.print("Set null stream for thread:");
         try {
             printerThread.setStream(null);

         } catch (NullPointerException e) {
             System.out.println(e.getMessage());

         }
     }

    public static void testSetInterval() {
        System.out.print("Set 0 interval for runnable:");
        try {
            printerRunnable.setInterval(0);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
        System.out.print("Set 0 interval for thread:");
        try {
            printerThread.setInterval(0);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }

    public static void testSetCount() {
        System.out.print("Set 0 count for runnable:");
        try {
            printerRunnable.setInterval(0);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
        System.out.print("Set 0 count for thread:");
        try {
            printerThread.setInterval(0);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }



    public static void testFileStream() {
         System.out.println("\nSetting print stream in file: ");
      try(FileOutputStream output = new FileOutputStream(FILE_NAME);)      {
          NamePrinterThreadChild printer = new NamePrinterThreadChild();
          printer.setStream(new PrintStream(output));
          printer.setCount(PRINT_COUNT);
          printer.setInterval(PRINT_INTERVAL);
          printer.setPrintName("C - File printer");
          printer.start();
          printer.join();
          System.out.println("Stream writed its name into file: " + FILE_NAME );

      }
      catch (IOException | InterruptedException e) {
          System.out.println(e.getMessage());
      }
        try( Scanner scanner = new Scanner(new FileInputStream(FILE_NAME))) {
          System.out.println("Checking file: "+ FILE_NAME);
          int counter = 0;
          String line;
          while(scanner.hasNextLine()){
              line = scanner.nextLine();
              counter++;
          }
          System.out.println("Counted lines are correct: " + (counter==PRINT_COUNT));

      }
      catch(FileNotFoundException e) {
          System.out.println(e.getMessage());
      }

    }

    public static void main(String[]args) {
      testCreate();
      testSetName();
      testSetStream();
      testSetInterval();
      testFileStream();
     }


}
