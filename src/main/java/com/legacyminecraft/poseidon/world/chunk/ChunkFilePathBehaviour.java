package com.legacyminecraft.poseidon.world.chunk;

import java.io.File;

/**
 * Canonical behaviour for legacy chunk file path resolution.
 */
public final class ChunkFilePathBehaviour {
    private static final ChunkFilePathBehaviour INSTANCE = new ChunkFilePathBehaviour();

    private ChunkFilePathBehaviour() {
    }

    public static ChunkFilePathBehaviour getInstance() {
        return INSTANCE;
    }

    public File resolveChunkFile(File chunkRootFolder, boolean createDirectories, int chunkX, int chunkZ) {
        String chunkFileName = "c." + Integer.toString(chunkX, 36) + "." + Integer.toString(chunkZ, 36) + ".dat";
        String firstLevelFolderName = Integer.toString(chunkX & 63, 36);
        String secondLevelFolderName = Integer.toString(chunkZ & 63, 36);

        File firstLevelFolder = new File(chunkRootFolder, firstLevelFolderName);
        if (!firstLevelFolder.exists()) {
            if (!createDirectories) {
                return null;
            }

            firstLevelFolder.mkdir();
        }

        File secondLevelFolder = new File(firstLevelFolder, secondLevelFolderName);
        if (!secondLevelFolder.exists()) {
            if (!createDirectories) {
                return null;
            }

            secondLevelFolder.mkdir();
        }

        File chunkFile = new File(secondLevelFolder, chunkFileName);
        return !chunkFile.exists() && !createDirectories ? null : chunkFile;
    }
}
