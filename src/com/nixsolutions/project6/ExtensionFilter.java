package com.nixsolutions.project6;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Created by annnikon on 02.02.17.
 */
public class ExtensionFilter implements FileFilter {
    private static final String EMPTY_STRING ="";
    private String ext;

    public ExtensionFilter(String ext) {
        if (ext==null) {
            this.ext=EMPTY_STRING;
        }
        else {
            this.ext=ext;
        }
    }

    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code>
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getPath().endsWith(ext)
                || pathname.isDirectory();
    }
}
