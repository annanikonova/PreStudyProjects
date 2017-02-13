package com.nixsolutions.project7.executor;

import com.nixsolutions.project6.IOUtilsImpl;
import interfaces.task7.executor.CopyTask;
import interfaces.task7.executor.SumTask;
import interfaces.task7.executor.Task;

import java.io.*;
import java.util.Random;


/**
 * Created by annnikon on 07.02.17.
 */
public class Tester {

    public static void main(String[]args) {
        testTryCount();
        testSum();
        testCopy();
        testStorage();

    }

    private static void testTryCount() {
        Task t1 = new SumTaskImpl();
        Task t2 = new CopyTaskImpl();
        System.out.println("Try count of new task 1:"+t1.getTryCount());
        System.out.println("Try count of new task 2:"+t2.getTryCount());
        t1.incTryCount();
        t2.incTryCount();
        System.out.println("After incr in task 1:"+t1.getTryCount());
        System.out.println("After incr in task 2:"+t2.getTryCount());
    }

    private static void testSum() {
        SumTask t1 = new SumTaskImpl();
        System.out.print("Seted 0 as max value: ");
        try {
            t1.setMax(0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        t1.setCount(0);
        t1.setMax(1);
        System.out.print("Seted 1 as max value and 0 as count: ");
        try {
            System.out.println("Success: " + t1.execute());
            System.out.println("Result: " + t1.getResult().longValue());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        t1.setCount(2);
        t1.setMax(10);
        System.out.print("Execute task for count = 2 and max = 10:");
        try {
            System.out.println("Success: "+ t1.execute());
            System.out.println("Result: " + t1.getResult().longValue());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Get random for max = 10: " + t1.getRandom().longValue());
        t1.setMax(1000);
        System.out.println("Get random for max = 1000: " + t1.getRandom());
        System.out.println("Get another random for max = 1000: " + t1.getRandom());
        t1.setMax(Long.MAX_VALUE);
        System.out.println("Get random for max long: " + t1.getRandom().longValue());


    }

    public static void testCopy() {
        IOUtilsImpl utils = new IOUtilsImpl();
        CopyTask task = new CopyTaskImpl();
        System.out.print("Execute new copy task without setting source: ");
        try {
            System.out.println("Success: " + task.execute());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Set null source: ");
        try {
            task.setSource(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Set null dest: ");
        try {
            task.setDest(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Set source directory, not file: ");
        try {
            task.setSource(System.getProperty("user.dir"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause().getMessage());
        }
        String source = "testSource.dat";
        String dest = "testDest.dat";
        System.out.println("Created some content for file: " + source);
        createSomeContent(source);
        System.out.println("File length: " + new File(source).length());
        task.setSource(source);
        task.setDest(dest);

        try {
            System.out.println("Copying done: "+ task.execute());
            System.out.println("Copy file size: " + new File(dest).length());
            System.out.println("Files are equal: " + utils.checkFilesEquals(source,dest) );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Try count of  this task is:" + task.getTryCount());
    }

    public static void createSomeContent(String source){

        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(source));) {

            Random random = new Random();
            byte[] buffer = new byte[1024];
            for (int i = 0; i < 1000; i++) {
                random.nextBytes(buffer);
                outputStream.write(buffer);
            }
            outputStream.write(123);

        } catch (IOException e) {
            System.out.println("Cannot create content: " + e.getMessage());
        }
    }

    public static void testStorage() {
        TaskStorageImpl storage = new TaskStorageImpl();
        System.out.println("Created new task  storage with count: "+storage.count());
        System.out.print("Get task from empty storage: ");
        try {
            System.out.println(storage.get());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Add null for storage: ");
        try {
            storage.add(null);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        Task copyTask = new CopyTaskImpl();
        Task sumTask = new SumTaskImpl();
        storage.add(copyTask);
        System.out.println("Storage count after adding copy task: "+storage.count());
        storage.add(sumTask);
        System.out.println("Storage count after adding sum task: "+storage.count());
        Task getFirst = storage.get();
        System.out.println("First task was taken from storage: "+ getFirst.equals(copyTask));
        System.out.println("Storage count after removing task: "+storage.count());
        Task getSecond = storage.get();
        System.out.println("Second task was taken from storage: "+ getSecond.equals(sumTask));
        System.out.println("Storage count after removing task: "+storage.count());
        Task getThird = storage.get();
        System.out.println("Unexisting task is null: "+ (getThird==null));
        System.out.println("Storage count: "+storage.count());


    }
}
