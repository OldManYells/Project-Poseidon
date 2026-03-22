package com.legacyminecraft.poseidon.world;

import net.minecraft.server.RegionFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;

public final class RegionFileCacheBehaviour {
    private static final RegionFileCacheBehaviour INSTANCE = new RegionFileCacheBehaviour();

    private RegionFileCacheBehaviour() {
    }

    public static RegionFileCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public RegionFile getOrCreate(Map cache, File worldFolder, int chunkX, int chunkZ) {
        File regionFolder = new File(worldFolder, "region");
        File regionPath = new File(regionFolder, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mcr");
        Reference reference = (Reference) cache.get(regionPath);
        RegionFile regionfile;

        if (reference != null) {
            regionfile = (RegionFile) reference.get();
            if (regionfile != null) {
                return regionfile;
            }
        }

        if (!regionFolder.exists()) {
            regionFolder.mkdirs();
        }

        if (cache.size() >= 256) {
            this.closeAndClear(cache);
        }

        regionfile = new RegionFile(regionPath);
        cache.put(regionPath, new SoftReference(regionfile));
        return regionfile;
    }

    public void closeAndClear(Map cache) {
        Iterator iterator = cache.values().iterator();

        while (iterator.hasNext()) {
            Reference reference = (Reference) iterator.next();

            try {
                RegionFile regionfile = (RegionFile) reference.get();

                if (regionfile != null) {
                    regionfile.b();
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }

        cache.clear();
    }

    public int getRegionBytesWritten(Map cache, File worldFolder, int chunkX, int chunkZ) {
        RegionFile regionfile = this.getOrCreate(cache, worldFolder, chunkX, chunkZ);
        return regionfile.a();
    }

    public DataInputStream openChunkInput(Map cache, File worldFolder, int chunkX, int chunkZ) {
        RegionFile regionfile = this.getOrCreate(cache, worldFolder, chunkX, chunkZ);
        return regionfile.a(chunkX & 31, chunkZ & 31);
    }

    public DataOutputStream openChunkOutput(Map cache, File worldFolder, int chunkX, int chunkZ) {
        RegionFile regionfile = this.getOrCreate(cache, worldFolder, chunkX, chunkZ);
        return regionfile.b(chunkX & 31, chunkZ & 31);
    }
}
