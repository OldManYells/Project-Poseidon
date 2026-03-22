package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ChunkFileNamingBehaviour;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

class ChunkFileFilter implements FileFilter {
    private static final ChunkFileNamingBehaviour CHUNK_FILE_NAMING_BEHAVIOUR = ChunkFileNamingBehaviour.getInstance();

    public static final Pattern a = Pattern.compile("[0-9a-z]|([0-9a-z][0-9a-z])");

    private ChunkFileFilter() {}

    public boolean accept(File file1) {
        return CHUNK_FILE_NAMING_BEHAVIOUR.isChunkFolder(file1, a);
    }

    ChunkFileFilter(EmptyClass2 emptyclass2) {
        this();
    }
}
