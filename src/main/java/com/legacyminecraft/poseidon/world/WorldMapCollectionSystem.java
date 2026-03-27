package com.legacyminecraft.poseidon.world;

import java.util.List;
import java.util.Map;

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
        // no-op in lean migration scaffold
    }

    public void loadIdCounts(IDataManager dataManager, Map idCounts) {
        idCounts.clear();
    }

    public int nextId(String key, Map idCounts, IDataManager dataManager) {
        Short value = (Short) idCounts.get(key);
        if (value == null) {
            value = Short.valueOf((short) 0);
        } else {
            value = Short.valueOf((short) (value.shortValue() + 1));
        }

        idCounts.put(key, value);
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
