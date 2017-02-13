package com.nixsolutions.project7.executor;

import com.sun.jmx.remote.internal.ArrayQueue;
import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by annnikon on 07.02.17.
 */
public class TaskStorageImpl implements TasksStorage
{
    public static final ReentrantLock LOCK = new ReentrantLock();
    private ConcurrentLinkedQueue<Task> queue = new ConcurrentLinkedQueue<Task>();

    @Override
    public void add(Task task) {
        if (task==null) {
            throw new NullPointerException("Cannot add null task. ");
        }
        queue.add(task);
    }

    @Override
    public Task get() {
        if (queue.size()<1) {
            return null;
        }

        return queue.poll();
    }

    @Override
    public int count() {
        return queue.size();
    }
}
