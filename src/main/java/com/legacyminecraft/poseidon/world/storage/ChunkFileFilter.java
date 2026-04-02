package com.legacyminecraft.poseidon.world.storage;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChunkFileFilter implements FileFilter {

    public static final Pattern a = Pattern.compile("[0-9a-z]|([0-9a-z][0-9a-z])");

    public ChunkFileFilter() {}

    public boolean accept(File file1) {
        if (file1.isDirectory()) {
            Matcher matcher = a.matcher(file1.getName());

            return matcher.matches();
        } else {
            return false;
        }
    }
}
