package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldConversionWrapperThinnessTest {
    private static final Path WORLD_LOADER_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldLoaderServer.java");

    @Test
    public void worldLoaderServerDelegatesFormatConversionFlowToCanonicalSystem() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_LOADER_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldFormatConversionSystem"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.createServerDataManager"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.isLegacyFormatConvertable"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.scanConversionWorkload"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.totalConversionCount"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.convertChunks"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.stampConvertedWorldVersion"));
        Assert.assertTrue(text.contains("WORLD_FORMAT_CONVERSION_SYSTEM.cleanupConvertedFolders"));
        Assert.assertFalse(text.contains("ChunkFileFilter chunkfilefilter = new ChunkFileFilter"));
        Assert.assertFalse(text.contains("DataInputStream datainputstream = new DataInputStream"));
        Assert.assertFalse(text.contains("RegionFileCache.a();"));
        Assert.assertFalse(text.contains("worlddata.a(19132)"));
    }
}
