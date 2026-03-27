package com.legacyminecraft.poseidon.world;

import java.io.File;

/**
 * Minimal legacy chunk file descriptor used by conversion and sorting policies.
 */
public class ChunkFile implements Comparable<ChunkFile> {
    private final File file;
    private final int chunkX;
    private final int chunkZ;

    public ChunkFile(File file) {
        this.file = file;
        int[] parsed = parseCoordinates(file == null ? null : file.getName());
        this.chunkX = parsed[0];
        this.chunkZ = parsed[1];
    }

    public File a() {
        return file;
    }

    public int b() {
        return chunkX;
    }

    public int c() {
        return chunkZ;
    }

    @Override
    public int compareTo(ChunkFile other) {
        if (other == null) {
            return 1;
        }
        int xCompare = this.chunkX - other.chunkX;
        return xCompare != 0 ? xCompare : this.chunkZ - other.chunkZ;
    }

    private int[] parseCoordinates(String fileName) {
        if (fileName == null) {
            return new int[]{0, 0};
        }

        int firstDot = fileName.indexOf('.');
        int secondDot = firstDot < 0 ? -1 : fileName.indexOf('.', firstDot + 1);
        int thirdDot = secondDot < 0 ? -1 : fileName.indexOf('.', secondDot + 1);

        if (firstDot < 0 || secondDot < 0 || thirdDot < 0) {
            return new int[]{0, 0};
        }

        try {
            int x = Integer.parseInt(fileName.substring(firstDot + 1, secondDot), 36);
            int z = Integer.parseInt(fileName.substring(secondDot + 1, thirdDot), 36);
            return new int[]{x, z};
        } catch (NumberFormatException exception) {
            return new int[]{0, 0};
        }
    }
}
