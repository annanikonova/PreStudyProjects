package com.nixsolutions.project8;

/**
 * Created by annnikon on 13.02.17.
 */

import com.nixsolutions.project6.IOUtilsImpl;
import interfaces.task8.PathClassLoader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class PathClassLoaderImpl extends ClassLoader implements PathClassLoader {
    private final static String CLASS_EXTENSION = ".class";
    private static IOUtilsImpl utils = new IOUtilsImpl();
    private ClassLoader parent;
    private File classesDir;
    private String path;
    private String[] files;
    private HashMap<String, String> filesMap;


    public Class loadClass(String name) throws ClassNotFoundException {
        if (name == null) {
            throw new NullPointerException("Null class name given");
        }
        Class<?> result = null;
        if (path == null) {
            System.out.println("Path is not set. Loading will be delegated for parent loader: "
                    + parent.getClass());
            return parent.loadClass(name);
        }
        this.files = utils.findFiles(path, CLASS_EXTENSION);
        if (files == null || files.length == 0) {
            throw new ClassNotFoundException("no class files in directory: " + path);
        }
        this.filesMap = new HashMap<String, String>();
        for (String filePath : files) {
            filesMap.put(getClassName(filePath), filePath);
        }
        if (!filesMap.containsKey(name)) {
            throw new ClassNotFoundException("No class: " + name + " in directory: " + path);
        }

        try {
            URL dirUrl = classesDir.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{dirUrl});
            result = loader.loadClass(name);
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        return result;

    }

    public PathClassLoaderImpl() {
        super(Thread.currentThread().getContextClassLoader());
        this.parent = Thread.currentThread().getContextClassLoader();
    }


    @Override
    public void setPath(String dir) {
        if (dir == null) {
            throw new NullPointerException("Null dir given. ");
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            throw new IllegalArgumentException("Directory not exists: " + dir);
        }
        if (!dirFile.isDirectory()) {
            throw new IllegalArgumentException("Not a dir: " + dir);
        }
        this.parent = Thread.currentThread().getContextClassLoader();
        this.classesDir = dirFile;
        this.path = classesDir.getAbsolutePath();
        this.files = utils.findFiles(path, CLASS_EXTENSION);

    }

    // separators in subdirs will be replaced for dots as package names
    public String getClassName(String fileAbsolutePath) {
        String fileRelativePath = fileAbsolutePath.replaceAll(path, "");
        String fileNameWithoutSlaches = fileRelativePath.replaceAll(File.separator, ".");
        String result = fileNameWithoutSlaches.replaceAll(CLASS_EXTENSION, "");
        result = result.replaceFirst(".", "");
        return result;
    }

    @Override
    public String getPath() {
        return path;
    }
}