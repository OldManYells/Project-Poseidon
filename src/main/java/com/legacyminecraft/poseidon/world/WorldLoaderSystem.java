package com.legacyminecraft.poseidon.world;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Canonical world-loader filesystem behavior for legacy WorldLoader wrappers.
 */
public final class WorldLoaderSystem {
    private static final WorldLoaderSystem INSTANCE = new WorldLoaderSystem();

    private WorldLoaderSystem() {
    }

    public static WorldLoaderSystem getInstance() {
        return INSTANCE;
    }

    public void ensureRootExists(File root) {
        if (!root.exists()) {
            root.mkdirs();
        }
    }

    public WorldData loadWorldData(File root, String worldName) {
        File worldDir = new File(root, worldName);
        if (!worldDir.exists()) {
            return null;
        }

        WorldData fromPrimary = loadFromFile(new File(worldDir, "level.dat"));
        if (fromPrimary != null) {
            return fromPrimary;
        }

        return loadFromFile(new File(worldDir, "level.dat_old"));
    }

    public void deleteTree(File[] files) {
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                deleteTree(files[i].listFiles());
            }
            files[i].delete();
        }
    }

    private WorldData loadFromFile(File dataFile) {
        if (!dataFile.exists()) {
            return null;
        }

        try {
            NBTTagCompound rootTag = CompressedStreamTools.a((InputStream) (new FileInputStream(dataFile)));
            NBTTagCompound dataTag = rootTag.k("Data");
            return new WorldData(dataTag);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
