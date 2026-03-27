package com.legacyminecraft.poseidon.world;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public <T> T loadWorldData(File worldDirectory) {
        File levelDat = new File(worldDirectory, "level.dat");
        if (levelDat.exists()) {
            T loaded = tryLoad(levelDat);
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

    public void saveWorldData(File worldDirectory, Object worldData) {
        writeAtomically(worldDirectory, WorldBridgeReflection.invoke(worldData, "a"));
    }

    public void saveWorldDataWithPlayerList(File worldDirectory, Object worldData, List playerList) {
        writeAtomically(worldDirectory, WorldBridgeReflection.invoke(worldData, "a", playerList));
    }

    private <T> T tryLoad(File sourceFile) {
        try (FileInputStream inputStream = new FileInputStream(sourceFile)) {
            Object root = WorldStorageCompatGatewayRegistry.gateway().readCompressed(inputStream);
            Object data = WorldStorageCompatGatewayRegistry.gateway().extractDataTag(root);
            return WorldBridgeReflection.cast(WorldStorageCompatGatewayRegistry.gateway().createWorldData(data));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void writeAtomically(File worldDirectory, Object worldDataTag) {
        try {
            File levelDatNew = new File(worldDirectory, "level.dat_new");
            File levelDatOld = new File(worldDirectory, "level.dat_old");
            File levelDat = new File(worldDirectory, "level.dat");

            Object root = WorldStorageCompatGatewayRegistry.gateway().createRootTag();
            WorldStorageCompatGatewayRegistry.gateway().setDataTag(root, worldDataTag);
            try (FileOutputStream outputStream = new FileOutputStream(levelDatNew)) {
                WorldStorageCompatGatewayRegistry.gateway().writeCompressed(root, outputStream);
            }

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
