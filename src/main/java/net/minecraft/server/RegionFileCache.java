package net.minecraft.server;

import com.legacyminecraft.poseidon.world.RegionFileCacheBehaviour;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RegionFileCache {
    private static final RegionFileCacheBehaviour REGION_FILE_CACHE_BEHAVIOUR = RegionFileCacheBehaviour.getInstance();

    private static final Map a = new HashMap();

    private RegionFileCache() {}

    public static synchronized RegionFile a(File file1, int i, int j) {
        return REGION_FILE_CACHE_BEHAVIOUR.getOrCreate(a, file1, i, j);
    }

    public static synchronized void a() {
        REGION_FILE_CACHE_BEHAVIOUR.closeAndClear(a);
    }

    public static int b(File file1, int i, int j) {
        return REGION_FILE_CACHE_BEHAVIOUR.getRegionBytesWritten(a, file1, i, j);
    }

    public static DataInputStream c(File file1, int i, int j) {
        return REGION_FILE_CACHE_BEHAVIOUR.openChunkInput(a, file1, i, j);
    }

    public static DataOutputStream d(File file1, int i, int j) {
        return REGION_FILE_CACHE_BEHAVIOUR.openChunkOutput(a, file1, i, j);
    }
}
