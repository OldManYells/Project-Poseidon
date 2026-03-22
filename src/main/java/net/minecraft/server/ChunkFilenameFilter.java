package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ChunkFileNamingBehaviour;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

class ChunkFilenameFilter implements FilenameFilter {
    private static final ChunkFileNamingBehaviour CHUNK_FILE_NAMING_BEHAVIOUR = ChunkFileNamingBehaviour.getInstance();

    public static final Pattern a = Pattern.compile("c\\.(-?[0-9a-z]+)\\.(-?[0-9a-z]+)\\.dat");

    private ChunkFilenameFilter() {}

    public boolean accept(File file1, String s) {
        return CHUNK_FILE_NAMING_BEHAVIOUR.isChunkDataFileName(a, s);
    }

    ChunkFilenameFilter(EmptyClass2 emptyclass2) {
        this();
    }
}
