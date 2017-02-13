package com.nixsolutions.project7.executor;

import com.nixsolutions.project6.IOUtilsImpl;
import interfaces.task7.executor.CopyTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.NoSuchElementException;

/**
 * Created by annnikon on 06.02.17.
 */
public class CopyTaskImpl implements CopyTask {
    private static int initialId = 0;
    private static IOUtilsImpl utils = new IOUtilsImpl();
    private File sourceFile, destFile;
    private int tryCount = 0;
    private int id = initialId++;

    @Override
    public void setSource(String source) {
        if (source == null) {
            throw new NullPointerException("Null source given.");
        }
        File file = new File(source);
        if (!file.exists()) {
            NoSuchElementException cause = new NoSuchElementException("No such file: " + source);
            throw new IllegalArgumentException(cause);
        }
        if (file.isDirectory()) {
            InvalidPathException cause = new InvalidPathException(source, " is directory");
            throw new IllegalArgumentException(cause);
        }

        this.sourceFile = file;
    }

    @Override
    public void setDest(String dest) {
        if (dest == null) {
            throw new NullPointerException("Null dest given.");
        }
        File file = new File(dest);
        if (file.isDirectory()) {
            InvalidPathException cause = new InvalidPathException(dest, " is directory");
            throw new IllegalArgumentException(cause);
        }

        this.destFile = file;

    }

    @Override
    public int getTryCount() {
        return tryCount;
    }

    @Override
    public void incTryCount() {
            tryCount++;
    }

    @Override
    public boolean execute() throws Exception {
        try {
            utils.copyFileBest(sourceFile.getPath(), destFile.getPath());

        } catch (NullPointerException | IllegalArgumentException e) {
            throw new Exception("Exception while copy task"+id+ "at try # " + tryCount, e);

        }
        if (!utils.checkFilesEquals(sourceFile.getPath(), destFile.getPath())) {
            System.out.println("\tResult of of copy task #"+id+" : wrong. Files not equal. ");
            return false;
        }
        System.out.println("\tResult of copy task #"+id+" : ok. Copy size:" + destFile.length());
        return true;

    }
}
