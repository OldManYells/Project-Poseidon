package com.legacyminecraft.poseidon.world.storage;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

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
