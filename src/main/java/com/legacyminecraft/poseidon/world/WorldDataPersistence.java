package com.legacyminecraft.poseidon.world;

import net.minecraft.server.CompressedStreamTools;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Canonical world-data persistence helpers (`level.dat` read/write).
 */
public final class WorldDataPersistence {
    private static final WorldDataPersistence INSTANCE = new WorldDataPersistence();

    private WorldDataPersistence() {
    }

    public static WorldDataPersistence getInstance() {
        return INSTANCE;
    }

    public WorldData loadWorldData(File worldDirectory) {
        File levelDat = new File(worldDirectory, "level.dat");
        if (levelDat.exists()) {
            WorldData loaded = tryLoad(levelDat);
            if (loaded != null) {
                return loaded;
            }
        }

        File levelDatOld = new File(worldDirectory, "level.dat_old");
        if (levelDatOld.exists()) {
            return tryLoad(levelDatOld);
        }

        return null;
    }

    public void saveWorldData(File worldDirectory, WorldData worldData) {
        NBTTagCompound data = worldData.a();
        writeAtomically(worldDirectory, data);
    }

    public void saveWorldDataWithPlayerList(File worldDirectory, WorldData worldData, List playerList) {
        NBTTagCompound data = worldData.a(playerList);
        writeAtomically(worldDirectory, data);
    }

    private WorldData tryLoad(File sourceFile) {
        try {
            NBTTagCompound root = CompressedStreamTools.a((InputStream) (new FileInputStream(sourceFile)));
            NBTTagCompound data = root.k("Data");
            return new WorldData(data);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void writeAtomically(File worldDirectory, NBTTagCompound worldDataTag) {
        NBTTagCompound root = new NBTTagCompound();
        root.a("Data", (NBTBase) worldDataTag);

        try {
            File levelDatNew = new File(worldDirectory, "level.dat_new");
            File levelDatOld = new File(worldDirectory, "level.dat_old");
            File levelDat = new File(worldDirectory, "level.dat");

            CompressedStreamTools.a(root, (OutputStream) (new FileOutputStream(levelDatNew)));
            if (levelDatOld.exists()) {
                levelDatOld.delete();
            }

            levelDat.renameTo(levelDatOld);
            if (levelDat.exists()) {
                levelDat.delete();
            }

            levelDatNew.renameTo(levelDat);
            if (levelDatNew.exists()) {
                levelDatNew.delete();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
