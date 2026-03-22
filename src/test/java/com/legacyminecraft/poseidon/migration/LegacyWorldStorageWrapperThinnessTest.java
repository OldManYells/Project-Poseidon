package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldStorageWrapperThinnessTest {
    private static final Path CHUNK_FILE_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkFile.java");
    private static final Path CHUNK_FILE_FILTER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkFileFilter.java");
    private static final Path CHUNK_FILENAME_FILTER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkFilenameFilter.java");
    private static final Path CHUNK_BUFFER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkBuffer.java");
    private static final Path REGION_FILE_CACHE_PATH = Paths.get("src/main/java/net/minecraft/server/RegionFileCache.java");
    private static final Path SERVER_NBT_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/ServerNBTManager.java");

    @Test
    public void chunkFileAndFiltersDelegateNamingAndParsingToCanonicalBehaviour() throws IOException {
        String chunkFile = new String(Files.readAllBytes(CHUNK_FILE_PATH), StandardCharsets.UTF_8);
        String chunkFileFilter = new String(Files.readAllBytes(CHUNK_FILE_FILTER_PATH), StandardCharsets.UTF_8);
        String chunkFilenameFilter = new String(Files.readAllBytes(CHUNK_FILENAME_FILTER_PATH), StandardCharsets.UTF_8);
        String chunkBuffer = new String(Files.readAllBytes(CHUNK_BUFFER_PATH), StandardCharsets.UTF_8);
        String regionFileCache = new String(Files.readAllBytes(REGION_FILE_CACHE_PATH), StandardCharsets.UTF_8);
        String serverNbtManager = new String(Files.readAllBytes(SERVER_NBT_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(chunkFile.contains("ChunkFileNamingBehaviour"));
        Assert.assertTrue(chunkFile.contains("CHUNK_FILE_NAMING_BEHAVIOUR.parseChunkFileCoordinates"));
        Assert.assertTrue(chunkFile.contains("CHUNK_FILE_NAMING_BEHAVIOUR.compare"));
        Assert.assertFalse(chunkFile.contains("matcher.matches()"));
        Assert.assertFalse(chunkFile.contains("Integer.parseInt(matcher.group(1), 36)"));

        Assert.assertTrue(chunkFileFilter.contains("CHUNK_FILE_NAMING_BEHAVIOUR.isChunkFolder"));
        Assert.assertFalse(chunkFileFilter.contains("file1.isDirectory()"));

        Assert.assertTrue(chunkFilenameFilter.contains("CHUNK_FILE_NAMING_BEHAVIOUR.isChunkDataFileName"));
        Assert.assertFalse(chunkFilenameFilter.contains("Matcher matcher = a.matcher(s)"));

        Assert.assertTrue(chunkBuffer.contains("RegionChunkBufferBehaviour"));
        Assert.assertTrue(chunkBuffer.contains("REGION_CHUNK_BUFFER_BEHAVIOUR.flushToRegion"));
        Assert.assertFalse(chunkBuffer.contains("this.a.a(this.b, this.c, this.buf, this.count)"));

        Assert.assertTrue(regionFileCache.contains("RegionFileCacheBehaviour"));
        Assert.assertTrue(regionFileCache.contains("REGION_FILE_CACHE_BEHAVIOUR.getOrCreate"));
        Assert.assertTrue(regionFileCache.contains("REGION_FILE_CACHE_BEHAVIOUR.closeAndClear"));
        Assert.assertTrue(regionFileCache.contains("REGION_FILE_CACHE_BEHAVIOUR.openChunkInput"));
        Assert.assertTrue(regionFileCache.contains("REGION_FILE_CACHE_BEHAVIOUR.openChunkOutput"));
        Assert.assertFalse(regionFileCache.contains("new File(file2, \"r.\" + (i >> 5) + \".\" + (j >> 5) + \".mcr\")"));
        Assert.assertFalse(regionFileCache.contains("cache.size() >= 256"));

        Assert.assertTrue(serverNbtManager.contains("ServerNbtManagerBehaviour"));
        Assert.assertTrue(serverNbtManager.contains("SERVER_NBT_MANAGER_BEHAVIOUR.createChunkLoader"));
        Assert.assertTrue(serverNbtManager.contains("SERVER_NBT_MANAGER_BEHAVIOUR.stampWorldVersion"));
        Assert.assertTrue(serverNbtManager.contains("SERVER_NBT_MANAGER_BEHAVIOUR.flushRegionCache"));
        Assert.assertFalse(serverNbtManager.contains("if (worldprovider instanceof WorldProviderHell)"));
        Assert.assertFalse(serverNbtManager.contains("worlddata.a(19132)"));
    }
}
