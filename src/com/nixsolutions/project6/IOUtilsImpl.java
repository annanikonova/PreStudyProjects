package com.nixsolutions.project6;

import interfaces.task6.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by annnikon on 02.02.17.
 */
public class IOUtilsImpl implements IOUtils {
    private static final int BUFFER_SIZE = 1024;
    @Override
    public void replaceChars(Reader reader, Writer writer, String s, String s1) {
        if (reader == null || writer == null) {
            throw new NullPointerException("Null reader or writer given. ");
        }
        String inChars = (s == null) ? "" : s;
        String outChars = (s1 == null) ? "" : s1;
        if (inChars.length() != outChars.length()) {
            throw new IllegalArgumentException("In and out strings should be the same length");
        }
        try {
            int i;
            int replacedCount = 0;
            while ((i = reader.read()) != -1) {
                char current = (char) i;

                if (replacedCount < inChars.length()
                        && current == inChars.charAt(replacedCount)) {

                    current = outChars.charAt(replacedCount++);

                }
                writer.write(current);

            }
        } catch (IOException e) {
            System.out.println("Error inside reading stream: " + e);
        }


    }


    @Override
    public String[] findFiles(String dirname) {
        return findFiles(dirname, null);
    }

    @Override
    public String[] findFiles(String dirname, String extension) {

        if (dirname == null) {
            throw new NullPointerException("Null directory given");
        }
        ArrayList<String> result = new ArrayList<>();
        File directory = new File(dirname);
        FileFilter onlyExt = new ExtensionFilter(extension);

        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory '" + dirname
                    + "' not exists in " + directory.getAbsolutePath());
        }

        File[] files = directory.listFiles(onlyExt);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File currentFile = files[i];
                String fullPath = currentFile.getAbsolutePath();
                if (!currentFile.isDirectory()) {
                    result.add(fullPath);
                } else {
                    //recursively do the same thing with subdirs
                    String[] subdirFiles = findFiles(fullPath, extension);
                    for (String fileString : subdirFiles) {
                        result.add(fileString);
                    }
                }
            }
        }

        return result.toArray(new String[0]);
    }


    /**
     * Copying files using usual byte streams.
     */
    @Override
    public void copyFile(String source, String dest) {
        if (source == null || dest == null) {
            throw new NullPointerException("Null parameter given.");
        }
        try ( InputStream reader = new FileInputStream(new File(source));
        OutputStream writer = new FileOutputStream(new File(dest))) {
        int readed;
            while ((readed=reader.read())!=-1)
            {
                writer.write(readed);
            }

        }
     catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Cannot find file for reading. ", e);
    } catch (IOException e) {
        throw new IllegalArgumentException("Cannot create file for writing. ", e);
    }

}

    /**
     * Copying files using buffered byte streams.
     * Faster that usual copying.
     * Faster than NIO copying.
     * Slower than best copying.
     */
    @Override
    public void copyFileBuffered(File source, File dest) {
        if (source == null || dest == null) {
            throw new NullPointerException("Null parameter given.");
        }

        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(dest));) {

            int read;
            while ((read=reader.read())!=-1)
            {
                writer.write(read);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Wrong path given. ", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create destination file.", e);
        }


    }

    /**
     * Copying file using byte array buffer
     * showed the best speed in tests for most files.
     */
    @Override
    public void copyFileBest(String source, String dest) {
        if (source == null || dest == null) {
            throw new NullPointerException("Null parameter given.");
        }
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest);) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Wrong path given. ", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create destination file. ", e);
        }
    }

    /**
     * Copying using channels. Slower than Buffered and my copying.
     */
    public void copyFileChannels(String source, String dest) {

        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel();) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Wrong path given. ", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create destination file. ", e);
        }
    }

    /**
     * Copying using Files.copy(). Fast, but slower than Buffered.
     */
    public void copyFileBestNIO(String source, String dest) {
        if (source == null || dest == null) {
            throw new NullPointerException("Null parameter given.");
        }
        try {
            Files.copy(Paths.get(source),
                    new FileOutputStream(dest));
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Wrong path given. ", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create destination file.", e);
        }

    }

    public boolean checkFilesEquals(String sourseFilePath, String destFilePath)  {
        byte[] bufferSource = new byte[BUFFER_SIZE];
        byte[] bufferDest = new byte[BUFFER_SIZE];
        try(InputStream inSourse = new FileInputStream(sourseFilePath);
            InputStream inDest = new FileInputStream(destFilePath);) {
            int count;
            int countCopy;
            while ((count = inSourse.read(bufferSource)) != -1) {
                countCopy= inDest.read(bufferDest);
                if (count!=countCopy) {
                    System.out.println("Different sizes. ");
                    return false;

                }
                if (!Arrays.equals(bufferSource,bufferDest)) {
                    System.out.println("Different content. ");
                    return false;
                }

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        return true;

    }


}
