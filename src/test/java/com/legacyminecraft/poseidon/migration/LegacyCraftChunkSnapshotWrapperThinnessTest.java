package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftChunkSnapshotWrapperThinnessTest {
    private static final Path CRAFT_CHUNK_SNAPSHOT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftChunkSnapshot.java");

    @Test
    public void craftChunkSnapshotDelegatesPackedDataAndColumnLookupsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_SNAPSHOT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeConversionBehaviour"));
        Assert.assertTrue(text.contains("BIOME_CONVERSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("biomeBaseToBiome(biomeBase)"));
        Assert.assertTrue(text.contains("ChunkSnapshotDataAccessBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("readBlockTypeId(buf, x, y, z)"));
        Assert.assertTrue(text.contains("readBlockData(buf, BLOCKDATA_OFF, x, y, z)"));
        Assert.assertTrue(text.contains("readSkyLight(buf, SKYLIGHT_OFF, x, y, z)"));
        Assert.assertTrue(text.contains("readEmittedLight(buf, BLOCKLIGHT_OFF, x, y, z)"));
        Assert.assertTrue(text.contains("readHighestBlockY(hmap, x, z)"));
        Assert.assertTrue(text.contains("readClimateValue(biomeTemp, x, z)"));
        Assert.assertTrue(text.contains("readClimateValue(biomeRain, x, z)"));
        Assert.assertFalse(text.contains("buf[x << 11 | z << 7 | y] & 255"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + BLOCKDATA_OFF"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + SKYLIGHT_OFF"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + BLOCKLIGHT_OFF"));
        Assert.assertFalse(text.contains("hmap[z << 4 | x] & 255"));
        Assert.assertFalse(text.contains("CraftBlock.biomeBaseToBiome(biomeBase)"));
    }
}
