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

        Assert.assertTrue(text.contains("CraftChunkSnapshotFactoryBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_SNAPSHOT_FACTORY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createSnapshot("));
        Assert.assertTrue(text.contains("BiomeConversionBehaviour"));
        Assert.assertTrue(text.contains("BIOME_CONVERSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ChunkSnapshotBiomeLookupBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_BIOME_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBiome("));
        Assert.assertTrue(text.contains("ChunkSnapshotClimateLookupBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_CLIMATE_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getTemperature(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, biomeTemp, x, z)"));
        Assert.assertTrue(text.contains("getRainfall(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, biomeRain, x, z)"));
        Assert.assertTrue(text.contains("ChunkSnapshotBlockLookupBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_BLOCK_LOOKUP_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ChunkSnapshotPackedBufferLayoutBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_PACKED_BUFFER_LAYOUT_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBlockDataOffset()"));
        Assert.assertTrue(text.contains("getSkyLightOffset()"));
        Assert.assertTrue(text.contains("getBlockLightOffset()"));
        Assert.assertTrue(text.contains("getBlockTypeId(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, buf, x, y, z)"));
        Assert.assertTrue(text.contains("getBlockData("));
        Assert.assertTrue(text.contains("getSkyLight("));
        Assert.assertTrue(text.contains("getEmittedLight("));
        Assert.assertTrue(text.contains("getHighestBlockY(CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR, hmap, x, z)"));
        Assert.assertTrue(text.contains("ChunkSnapshotDataAccessBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_DATA_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ChunkSnapshotMetadataAccessBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_METADATA_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getX(x)"));
        Assert.assertTrue(text.contains("getZ(z)"));
        Assert.assertTrue(text.contains("getWorldName(worldname)"));
        Assert.assertTrue(text.contains("getCaptureFullTime(captureFulltime)"));
        Assert.assertFalse(text.contains("return new CraftChunkSnapshot("));
        Assert.assertFalse(text.contains("buf[x << 11 | z << 7 | y] & 255"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + BLOCKDATA_OFF"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + SKYLIGHT_OFF"));
        Assert.assertFalse(text.contains("((x << 10) | (z << 6) | (y >> 1)) + BLOCKLIGHT_OFF"));
        Assert.assertFalse(text.contains("private static final int BLOCKDATA_OFF"));
        Assert.assertFalse(text.contains("private static final int BLOCKLIGHT_OFF"));
        Assert.assertFalse(text.contains("private static final int SKYLIGHT_OFF"));
        Assert.assertFalse(text.contains("hmap[z << 4 | x] & 255"));
        Assert.assertFalse(text.contains("CraftBlock.biomeBaseToBiome(biomeBase)"));
        Assert.assertFalse(text.contains("readBiome(biome, x, z, BIOME_RESOLVER)"));
        Assert.assertFalse(text.contains("readClimateValue(biomeTemp, x, z)"));
        Assert.assertFalse(text.contains("readClimateValue(biomeRain, x, z)"));
        Assert.assertFalse(text.contains("readBlockTypeId(buf, x, y, z)"));
        Assert.assertFalse(text.contains("readBlockData(buf, BLOCKDATA_OFF, x, y, z)"));
        Assert.assertFalse(text.contains("readSkyLight(buf, SKYLIGHT_OFF, x, y, z)"));
        Assert.assertFalse(text.contains("readEmittedLight(buf, BLOCKLIGHT_OFF, x, y, z)"));
        Assert.assertFalse(text.contains("readHighestBlockY(hmap, x, z)"));
        Assert.assertFalse(text.contains("return x;"));
        Assert.assertFalse(text.contains("return z;"));
        Assert.assertFalse(text.contains("return worldname;"));
        Assert.assertFalse(text.contains("return captureFulltime;"));
    }
}
