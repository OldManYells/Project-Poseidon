package com.legacyminecraft.poseidon.world.storage;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChunkFilenameFilter implements FilenameFilter {

    public static final Pattern a = Pattern.compile("c\\.(-?[0-9a-z]+)\\.(-?[0-9a-z]+)\\.dat");

    public ChunkFilenameFilter() {}

    public boolean accept(File file1, String s) {
        Matcher matcher = a.matcher(s);

        return matcher.matches();
    }
}
