package com.nixsolutions.project7.executor;

import com.nixsolutions.project6.Executor;
import com.nixsolutions.project6.IOUtilsImpl;
import interfaces.task7.executor.CopyTask;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * Created by annnikon on 07.02.17.
 */
public class DemoTaskB {

    public static final int EXECUTORS_COUNT = 3;
    public static final int COPY_TASKS_COUNT = 5;
    public static final int SUM_TASKS_COUNT = 10;
    public static final int BUFFER_SIZE = 1024;
    public static final String SOURCE_NAME="bigFile.txt";
    public static final String DEST_NAME="bigFileCopy.txt";
    public static final int SLEEP_TIME = 100;
    public static volatile TaskStorageImpl storage = new TaskStorageImpl();
    public static ArrayList<ExecutorImpl> activeExecutors = new ArrayList<>();
    public static ReentrantLock lock = TaskStorageImpl.LOCK;

    public static void main(String[] args) {

        System.out.println("Main thread started. ");
        fillStorageFromThisThread();
        createBigFile(SOURCE_NAME);
        Thread anotherThread = fillerStorageThread();
        anotherThread.start();
        try {
            System.out.println("Waiting while another thread creates tasks");
            anotherThread.join();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Storage has: " + storage.count() + " tasks");
        createExecutors();
        while(storage.count()>0) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted: " + e.getMessage());
            }
        }
        stopExecutors();
        System.out.println("Storage has: " + storage.count() + " tasks");
        System.out.println("Main thread finished");


    }

    private static void fillStorageFromThisThread() {
       long initialMax = 10;
       int initialCount = 100;
        for (int i =0; i<SUM_TASKS_COUNT; i++ ) {
            SumTaskImpl task = new SumTaskImpl();
            long newMax = (++initialMax) * (i+1);
            int newCount = ++initialCount;
            task.setCount(newCount);
            task.setMax(newMax);
            System.out.println("Created sum task with count: "+newCount+
            " and max: "+ newMax);
            lock.lock();
            storage.add(task);
            lock.unlock();
        }

        System.out.println("Added "+SUM_TASKS_COUNT
                +" copy tasks into storage. Storage size is: " + storage.count());

    }

    private static void createBigFile(String filename) {
        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(filename));) {
            Random random = new Random();
            byte[] buffer = new byte[BUFFER_SIZE];
            for (int i = 0; i < BUFFER_SIZE*BUFFER_SIZE; i++) {
                random.nextBytes(buffer);
                outputStream.write(buffer);
            }
            outputStream.write(123);
            outputStream.close();

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Created a big file '"
                +filename+"' of size:" + new File(filename).length());
    }


    private static Thread fillerStorageThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Another thread for creating copy tasks started.");
                for (int i =0; i<COPY_TASKS_COUNT; i++ ) {
                    CopyTaskImpl task = new CopyTaskImpl();
                    task.setSource(SOURCE_NAME);
                    StringBuilder sb = new StringBuilder();
                   String[] parts = SOURCE_NAME.split(Pattern.quote("."));
                   if (parts.length<2) {
                       task.setDest(DEST_NAME);
                       System.out.println("Created copy task with dest: " + DEST_NAME);
                   }

                   else {
                       sb.append(parts[parts.length-2])
                               .append("Copy").append(i)
                               .append(".")
                               .append(parts[parts.length-1]);
                       task.setDest(sb.toString());
                       System.out.println("Created copy task with dest:" + sb.toString());
                  }

                  lock.lock();
                  storage.add(task);
                  lock.unlock();

                }
               System.out.println("Added "+COPY_TASKS_COUNT+
                       " copy tasks into storage. Storage size is: " + storage.count());
                System.out.println("Another thread finished. ");

            }
        });

    }


    private static void createExecutors() {
        for (int i = 0; i < EXECUTORS_COUNT; i++) {
            ExecutorImpl executor = new ExecutorImpl();
            executor.setStorage(storage);
            activeExecutors.add(executor);
            executor.start();
        }
    }

    private static void stopExecutors() {

        for (ExecutorImpl executor : activeExecutors) {
            if (executor.isActive()) {
                executor.stop();
            }
         }

        }

    }

