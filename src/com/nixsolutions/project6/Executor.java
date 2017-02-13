package com.nixsolutions.project6;


import java.io.*;
import java.util.*;

/**
 * Created by annnikon on 02.02.17.
 */
public class Executor {
    private static IOUtilsImpl utils = new IOUtilsImpl();

    public enum CopyMethods {usual, buffered, channel, nio, my}

    private static void printArray(String[] array) {
        for (String s : array) {
            System.out.println(s);
        }
    }

    public static void testReplace() {
        String s = "1234567890";
        try (Reader in = new CharArrayReader(s.toCharArray());
             Writer out = new CharArrayWriter(0);) {
            String inChars = "135", outChars = "abc";
            utils.replaceChars(in, out, inChars, outChars);
            System.out.print(String.format("\nReplace array '%s' for array '%s' ", inChars, outChars));
            System.out.println("in stream: " + s);
            System.out.println("Result is: " + out);

            System.out.print("\nReplace in null reader:");
            try {
                utils.replaceChars(null, out, inChars, outChars);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("\nReplace in different sized strings:");
            try {
                utils.replaceChars(in, out, inChars, null);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error creating streams: " + e);
        }

    }

    public static void createHyerarchy(String dirName,
                                       String subdirName1,
                                       String subdirName2) {
        System.out.println("Created parent folder: " + new File(dirName).mkdir());
        System.out.println("Created child1 folder: " + new File(subdirName1).mkdir());
        System.out.println("Created child2 folder: " + new File(subdirName2).mkdir());
        File parentFile = new File(dirName + File.separator + "1.txt");
        File child1File1 = new File(subdirName1 + File.separator + "2.html");
        File child1File2 = new File(subdirName1 + File.separator + "3.txt");
        try {
            System.out.println("Created .txt file in parent folder: "
                    + parentFile.createNewFile());
            System.out.println("Created .html file in child1 folder: "
                    + child1File1.createNewFile());
            System.out.println("Created .txt file in child1 folder: "
                    + child1File2.createNewFile());
        } catch (IOException e) {
            System.out.println("Cannot create files: " + e.getMessage());
        }
    }

    public static void testFind() {

        String dirName = "parent";
        String subdirName1 = dirName + File.separator + "child1";
        String subdirName2 = dirName + File.separator + "child2";

        createHyerarchy(dirName, subdirName1, subdirName2);

        System.out.println("\nFind files in dir with relative path: " + dirName);
        String[] findedFiles = utils.findFiles(dirName);
        printArray(findedFiles);
        String fullDirName = getWorkingDirectory() + File.separator + dirName;
        System.out.println("\nFor full path: " + fullDirName);
        String[] findedFiles2 = utils.findFiles(fullDirName);
        printArray(findedFiles2);
        System.out.println("Results are equal: " + Arrays.equals(findedFiles, findedFiles2));

        System.out.println("\nFind files in child1: ");
        String[] findedFilesChild1 = utils.findFiles(subdirName1);
        printArray(findedFilesChild1);
        System.out.println("Array length: " + findedFilesChild1.length);

        System.out.println("\nFind files in empty child2: ");
        String[] findedFilesChild2 = utils.findFiles(subdirName2);
        printArray(findedFilesChild2);
        System.out.println("Array length: " + findedFilesChild2.length);

    }

    public static void testFindOnly() {

        System.out.print("\nFind files in null:");
        try {
            printArray(utils.findFiles(null, null));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("\nFind files in invalid path:");
        try {
            printArray(utils.findFiles("#", ""));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nFind only .java files in current working directory:");
        printArray(utils.findFiles(getWorkingDirectory(), ".java"));

        System.out.println("\nFind .tmp files in temp directory:");
        printArray(utils.findFiles(getTempDirectory(), ".tmp"));

    }


    public static void testCopySpeed() {

        String source = utils.findFiles(getWorkingDirectory(), ".java")[0];
        System.out.println("\nTest copying file: " + source);
        System.out.println("File size: " + new File(source).length());
        TreeSet<CopyTester> tests = new TreeSet<>(new Comparator<CopyTester>() {
            public int compare(CopyTester o1, CopyTester o2) {
                return (int) (o1.getElapsedTime() - o2.getElapsedTime());
            }
        });
        tests.add(new CopyTester(source, "copyUsual", CopyMethods.usual));
        tests.add(new CopyTester(source, "copyBuffered", CopyMethods.buffered));
        tests.add(new CopyTester(source, "copyChannel", CopyMethods.channel));
        tests.add(new CopyTester(source, "copyNIO", CopyMethods.nio));
        tests.add(new CopyTester(source, "copyMy", CopyMethods.my));

        System.out.println("Sorted by speed: ");
        for (CopyTester test : tests) {
            System.out.println(test);
        }

        System.out.println("The best is: " + tests.first()); //The best is my method, the worst is usual

    }

    public static void createSomeContent(String filename) {
        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(filename));) {
            Random random = new Random();
            byte[] buffer = new byte[1024];
            for (int i = 0; i < 100; i++) {
                random.nextBytes(buffer);
                outputStream.write(buffer);
            }
            outputStream.write(123);
            outputStream.close();

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testCopy() {
        String sourceFileName = "parent/1.txt";
        String destFileName = "parent/1copy.txt";
        createSomeContent(sourceFileName);
        System.out.println("\nSource file was filled with some content: " + sourceFileName);
        System.out.println("Source file size: " + new File(sourceFileName).length());
        System.out.println("\nCopying to: " + destFileName);
        try {
            utils.copyFileBuffered(new File(sourceFileName), new File(destFileName));
            System.out.println("Copy file size: " + new File(destFileName).length());
            System.out.println("Compared files are equal:"+
                    utils.checkFilesEquals(sourceFileName,destFileName));
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        System.out.println("\nCopying to null: ");
        try {
            utils.copyFileBest(sourceFileName, null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nCopying into the directory, not file: " + getTempDirectory());
        try {
            utils.copyFileBuffered(new File(sourceFileName), new File(getTempDirectory()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
        }


    }

    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public static String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }

    public static void main(String[] args) {
        System.out.println("Working directory is: " + getWorkingDirectory());
        testFind();
        testFindOnly();
        testCopySpeed();
        testCopy();
        testReplace();


    }


}
