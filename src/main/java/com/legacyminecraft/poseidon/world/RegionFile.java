package com.legacyminecraft.poseidon.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * World-local region file scaffold for migrated region IO behaviours.
 */
public class RegionFile {
    private final File file;

    public RegionFile(File file) {
        this.file = file;
    }

    public int a() {
        return 0;
    }

    public DataInputStream a(int chunkX, int chunkZ) {
        return new DataInputStream(new ByteArrayInputStream(new byte[0]));
    }

    public DataOutputStream b(int chunkX, int chunkZ) {
        return new DataOutputStream(new ByteArrayOutputStream());
    }

    public void b() throws IOException {
    }

    public void poseidonWriteChunkData(int chunkX, int chunkZ, byte[] data, int size) {
    }

    public File getFile() {
        return file;
    }
}
