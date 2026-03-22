package com.legacyminecraft.poseidon.world;

import net.minecraft.server.ChunkFile;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChunkFileNamingBehaviour {
    private static final ChunkFileNamingBehaviour INSTANCE = new ChunkFileNamingBehaviour();

    private ChunkFileNamingBehaviour() {
    }

    public static ChunkFileNamingBehaviour getInstance() {
        return INSTANCE;
    }

    public ChunkCoordinates parseChunkFileCoordinates(Pattern pattern, String fileName) {
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.matches()) {
            return new ChunkCoordinates(Integer.parseInt(matcher.group(1), 36), Integer.parseInt(matcher.group(2), 36));
        }

        return new ChunkCoordinates(0, 0);
    }

    public int compare(ChunkFile left, ChunkFile right) {
        int i = left.b() >> 5;
        int j = right.b() >> 5;

        if (i == j) {
            int k = left.c() >> 5;
            int l = right.c() >> 5;

            return k - l;
        }

        return i - j;
    }

    public boolean isChunkFolder(File file, Pattern pattern) {
        if (!file.isDirectory()) {
            return false;
        }

        Matcher matcher = pattern.matcher(file.getName());
        return matcher.matches();
    }

    public boolean isChunkDataFileName(Pattern pattern, String fileName) {
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    public static final class ChunkCoordinates {
        public final int x;
        public final int z;

        ChunkCoordinates(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }
}
