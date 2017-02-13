package com.nixsolutions.project7.executor;

import interfaces.task7.executor.*;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by annnikon on 07.02.17.
 */
public class ExecutorImpl implements Runnable, interfaces.task7.executor.Executor {

    private static final int MAX_TRY_COUNT = 5;
    /**How often executor will check if the storage has tasks*/
    private static final int SLEEP_TIME = 100;


    private static int initialId = 1;

    private int id = initialId++;
    private TasksStorage storage; //common shared storage
    private volatile boolean isActive = false;
    private volatile boolean isTaskPerforming = false;
    private Thread thread = new Thread(this);
    private ReentrantLock lock;

    public Thread getThread() {
        return thread;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isTaskPerforming() {
        return isTaskPerforming;
    }

    @Override
    public void run() {
        System.out.println("Executor #" + id + " started");

        while (isActive) {
            if (storage.count() > 0 && !isTaskPerforming) {
                lock.lock();
                System.out.println("Executor #" + id + " takes task from storage of size: " + storage.count());
                Task currentTask = storage.get();
                System.out.println("Executor #" + id + " unlocks storage");
                lock.unlock();
                performTask(currentTask);
            }

            try {
                System.out.println("Executor #" + id + " sleeps for " + SLEEP_TIME + "ms");
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }


    synchronized private void performTask(Task currentTask) {
        if(currentTask == null) {
            System.out.println("Task is null");
            return;
        }
        isTaskPerforming = true;
        System.out.println("Executor #" + id + " performs task: " + currentTask.getClass().getSimpleName());
        boolean done = false;
        try {
            done = currentTask.execute();
        } catch (Exception e) {
            System.out.println("Executor #" + id +" exception in task: "+e.getMessage());
            returnToStorage(currentTask);
            isTaskPerforming=false;
        }
        if (!done) {
            returnToStorage(currentTask);
        }
        System.out.println("Executor # " + id + " Sucessfully: " + done + " after task count# " + currentTask.getTryCount());
        isTaskPerforming = false;
    }

    synchronized private void returnToStorage (Task currentTask) {
        if (currentTask.getTryCount() < MAX_TRY_COUNT) {
            System.out.println("Executor #" + id + " puts task "
                    +currentTask.getClass().getSimpleName()
                    +" into storage and increments its count");
            currentTask.incTryCount();
            lock.lock();
            storage.add(currentTask);
            lock.unlock();

        }

    }

    @Override
    public void setStorage(TasksStorage tasksStorage) {
       this.storage = tasksStorage;
       this.lock = TaskStorageImpl.LOCK;
    }

    @Override
    public TasksStorage getStorage() {
        return storage;
    }

    @Override
    public void start() {
        if (storage == null) {
            throw new NullPointerException("Storage is not set. ");
        }
        if (!isActive) {
            isActive = true;
            thread.start();
        } else {
            throw new IllegalStateException("Executor #" + id + " is already started. ");
        }
    }
    @Override
   synchronized public void stop() {
        if (!isActive) {
            throw new IllegalStateException("Executor #" + id + " is already stopped or wasn`t started.");
        }
        while (isTaskPerforming) {
           try{
               System.out.println("Executor #"+id+" is waiting until task finishes");
               Thread.sleep(SLEEP_TIME);
           }
           catch (InterruptedException e ) { System.out.println("Cannot sleep: "+e.getMessage());
           }

        }
        if (!isTaskPerforming) {
            System.out.println("Executor #" + id + " stopped");
            isActive = false;
        }

    }


}
