package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ChunkFileNamingBehaviour;

import java.io.File;

public class ChunkFile implements Comparable {
    private static final ChunkFileNamingBehaviour CHUNK_FILE_NAMING_BEHAVIOUR = ChunkFileNamingBehaviour.getInstance();

    private final File a;
    private final int b;
    private final int c;

    public ChunkFile(File file1) {
        this.a = file1;
        ChunkFileNamingBehaviour.ChunkCoordinates coordinates = CHUNK_FILE_NAMING_BEHAVIOUR.parseChunkFileCoordinates(ChunkFilenameFilter.a, file1.getName());
        this.b = coordinates.x;
        this.c = coordinates.z;
    }

    public int compareTo(Object o) {
        return CHUNK_FILE_NAMING_BEHAVIOUR.compare(this, (ChunkFile) o);
    }

    public File a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }
}
