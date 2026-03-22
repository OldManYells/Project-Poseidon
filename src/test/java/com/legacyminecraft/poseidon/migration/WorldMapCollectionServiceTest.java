package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.WorldMapCollectionSystem;
import net.minecraft.server.IChunkLoader;
import net.minecraft.server.IDataManager;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PlayerFileData;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldMapBase;
import net.minecraft.server.WorldProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WorldMapCollectionServiceTest {
    @Test(expected = RuntimeException.class)
    public void putMapRejectsNullMap() {
        WorldMapCollectionSystem service = WorldMapCollectionSystem.getInstance();
        service.putMap("map", null, new HashMap(), new ArrayList());
    }

    @Test
    public void nextIdPersistsAndLoadsIdCounts() {
        WorldMapCollectionSystem service = WorldMapCollectionSystem.getInstance();
        FileBackedDataManager dataManager = new FileBackedDataManager(createTempDir());
        Map idCounts = new HashMap();

        int first = service.nextId("map", idCounts, dataManager);
        int second = service.nextId("map", idCounts, dataManager);

        Assert.assertEquals(0, first);
        Assert.assertEquals(1, second);

        Map loadedCounts = new HashMap();
        service.loadIdCounts(dataManager, loadedCounts);
        Assert.assertEquals(Short.valueOf((short) 1), loadedCounts.get("map"));
    }

    @Test
    public void saveAndLoadMapRoundTripsPayloadAndCacheRegistration() {
        WorldMapCollectionSystem service = WorldMapCollectionSystem.getInstance();
        FileBackedDataManager dataManager = new FileBackedDataManager(createTempDir());
        Map loadedMaps = new HashMap();
        List trackedMaps = new ArrayList();

        DummyWorldMap expected = new DummyWorldMap("test_map");
        expected.value = 37;
        service.putMap("test_map", expected, loadedMaps, trackedMaps);
        service.saveMap(dataManager, expected);

        loadedMaps.clear();
        trackedMaps.clear();

        DummyWorldMap actual = (DummyWorldMap) service.getOrLoadMap(
                DummyWorldMap.class,
                "test_map",
                dataManager,
                loadedMaps,
                trackedMaps
        );

        Assert.assertNotNull(actual);
        Assert.assertEquals(37, actual.value);
        Assert.assertSame(actual, loadedMaps.get("test_map"));
        Assert.assertEquals(1, trackedMaps.size());
    }

    @Test
    public void flushDirtyMapsSavesAndClearsDirtyFlag() {
        WorldMapCollectionSystem service = WorldMapCollectionSystem.getInstance();
        FileBackedDataManager dataManager = new FileBackedDataManager(createTempDir());
        List trackedMaps = new ArrayList();
        DummyWorldMap dirtyMap = new DummyWorldMap("dirty_map");
        dirtyMap.value = 9;
        dirtyMap.a(true);
        trackedMaps.add(dirtyMap);

        service.flushDirtyMaps(trackedMaps, dataManager);

        Assert.assertFalse(dirtyMap.b());
        File persisted = dataManager.b("dirty_map");
        Assert.assertTrue(persisted.exists());
    }

    private File createTempDir() {
        File dir = new File(System.getProperty("java.io.tmpdir"), "poseidon-worldmap-" + System.nanoTime());
        dir.mkdirs();
        return dir;
    }

    public static final class DummyWorldMap extends WorldMapBase {
        private int value;

        public DummyWorldMap(String s) {
            super(s);
        }

        @Override
        public void a(NBTTagCompound nbttagcompound) {
            this.value = nbttagcompound.e("v");
        }

        @Override
        public void b(NBTTagCompound nbttagcompound) {
            nbttagcompound.a("v", this.value);
        }
    }

    private static final class FileBackedDataManager implements IDataManager {
        private final File dataDir;
        private final UUID uuid = UUID.randomUUID();

        private FileBackedDataManager(File dataDir) {
            this.dataDir = dataDir;
        }

        @Override
        public WorldData c() {
            return null;
        }

        @Override
        public void b() {
        }

        @Override
        public IChunkLoader a(WorldProvider worldprovider) {
            return null;
        }

        @Override
        public void a(WorldData worlddata, List list) {
        }

        @Override
        public void a(WorldData worlddata) {
        }

        @Override
        public PlayerFileData d() {
            return null;
        }

        @Override
        public void e() {
        }

        @Override
        public File b(String s) {
            return new File(dataDir, s + ".dat");
        }

        @Override
        public UUID getUUID() {
            return uuid;
        }
    }
}
