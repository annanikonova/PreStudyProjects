package com.nixsolutions.project6;

import java.io.File;

public class CopyTester {
    private IOUtilsImpl utils = new IOUtilsImpl();
    private Executor.CopyMethods method;
    private long ellapsedTime;

    public CopyTester(String source, String dest, Executor.CopyMethods method){
        this.method = method;
        this.ellapsedTime = measureTime(source,dest,method);

    }
    public long getElapsedTime() {
        return ellapsedTime;
    }

     private long measureTime(String source, String dest, Executor.CopyMethods method) {

        long startTime= System.nanoTime();
        switch (method) {
            case usual:
                utils.copyFile(source,dest); break;
            case buffered:
                utils.copyFileBuffered(new File(source),new File(dest)); break;
            case channel:
                utils.copyFileChannels(source,dest); break;
            case nio:
                utils.copyFileBestNIO(source,dest); break;
            case my:
                utils.copyFileBest(source,dest); break;
        }
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    public String toString() {
        return String.format("%s method : %d ns", method.toString(),ellapsedTime);
    }
}
