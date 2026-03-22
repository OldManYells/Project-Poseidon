package com.legacyminecraft.poseidon.world;

import net.minecraft.server.CompressedStreamTools;
import net.minecraft.server.IDataManager;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagShort;
import net.minecraft.server.WorldMapBase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.DataInput;
import java.io.DataOutput;

/**
 * Canonical map-data persistence and id-counter behavior for legacy WorldMapCollection wrappers.
 */
public final class WorldMapCollectionSystem {
    private static final WorldMapCollectionSystem INSTANCE = new WorldMapCollectionSystem();
    private static final String IDCOUNTS_FILE = "idcounts";
    private static final String DATA_TAG_KEY = "data";

    private WorldMapCollectionSystem() {
    }

    public static WorldMapCollectionSystem getInstance() {
        return INSTANCE;
    }

    public WorldMapBase getOrLoadMap(Class mapClass, String key, IDataManager dataManager, Map loadedMaps, List trackedMaps) {
        WorldMapBase cached = (WorldMapBase) loadedMaps.get(key);
        if (cached != null) {
            return cached;
        }

        WorldMapBase loaded = null;
        if (dataManager != null) {
            try {
                File mapFile = dataManager.b(key);
                if (mapFile != null && mapFile.exists()) {
                    loaded = instantiateMap(mapClass, key);
                    FileInputStream fileInputStream = new FileInputStream(mapFile);
                    NBTTagCompound rootTag = CompressedStreamTools.a((InputStream) fileInputStream);
                    fileInputStream.close();
                    loaded.a(rootTag.k(DATA_TAG_KEY));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (loaded != null) {
            loadedMaps.put(key, loaded);
            trackedMaps.add(loaded);
        }

        return loaded;
    }

    public void putMap(String key, WorldMapBase map, Map loadedMaps, List trackedMaps) {
        if (map == null) {
            throw new RuntimeException("Can\'t set null data");
        }

        if (loadedMaps.containsKey(key)) {
            trackedMaps.remove(loadedMaps.remove(key));
        }

        loadedMaps.put(key, map);
        trackedMaps.add(map);
    }

    public void flushDirtyMaps(List trackedMaps, IDataManager dataManager) {
        for (int i = 0; i < trackedMaps.size(); ++i) {
            WorldMapBase map = (WorldMapBase) trackedMaps.get(i);
            if (map.b()) {
                saveMap(dataManager, map);
                map.a(false);
            }
        }
    }

    public void saveMap(IDataManager dataManager, WorldMapBase map) {
        if (dataManager != null) {
            try {
                File mapFile = dataManager.b(map.a);
                if (mapFile != null) {
                    NBTTagCompound mapData = new NBTTagCompound();
                    map.b(mapData);
                    NBTTagCompound rootTag = new NBTTagCompound();
                    rootTag.a(DATA_TAG_KEY, mapData);

                    FileOutputStream fileOutputStream = new FileOutputStream(mapFile);
                    CompressedStreamTools.a(rootTag, (OutputStream) fileOutputStream);
                    fileOutputStream.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void loadIdCounts(IDataManager dataManager, Map idCounts) {
        try {
            idCounts.clear();
            if (dataManager == null) {
                return;
            }

            File idCountsFile = dataManager.b(IDCOUNTS_FILE);
            if (idCountsFile != null && idCountsFile.exists()) {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(idCountsFile));
                NBTTagCompound rootTag = CompressedStreamTools.a((DataInput) dataInputStream);
                dataInputStream.close();
                Iterator iterator = rootTag.c().iterator();
                while (iterator.hasNext()) {
                    NBTBase nbtBase = (NBTBase) iterator.next();
                    if (nbtBase instanceof NBTTagShort) {
                        NBTTagShort shortTag = (NBTTagShort) nbtBase;
                        idCounts.put(shortTag.b(), Short.valueOf(shortTag.a));
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int nextId(String key, Map idCounts, IDataManager dataManager) {
        Short value = (Short) idCounts.get(key);
        if (value == null) {
            value = Short.valueOf((short) 0);
        } else {
            value = Short.valueOf((short) (value.shortValue() + 1));
        }

        idCounts.put(key, value);
        if (dataManager == null) {
            return value.shortValue();
        }

        try {
            File idCountsFile = dataManager.b(IDCOUNTS_FILE);
            if (idCountsFile != null) {
                NBTTagCompound rootTag = new NBTTagCompound();
                Iterator iterator = idCounts.keySet().iterator();
                while (iterator.hasNext()) {
                    String idKey = (String) iterator.next();
                    short idValue = ((Short) idCounts.get(idKey)).shortValue();
                    rootTag.a(idKey, idValue);
                }

                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(idCountsFile));
                CompressedStreamTools.a(rootTag, (DataOutput) dataOutputStream);
                dataOutputStream.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return value.shortValue();
    }

    private WorldMapBase instantiateMap(Class mapClass, String key) throws Exception {
        try {
            return (WorldMapBase) mapClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{key});
        } catch (Exception exception) {
            throw new RuntimeException("Failed to instantiate " + mapClass.toString(), exception);
        }
    }
}
