package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyRegionFileWrapperThinnessTest {
    private static final Path REGION_FILE_PATH =
            Paths.get("src/main/java/net/minecraft/server/RegionFile.java");

    @Test
    public void regionFileDelegatesChunkReadOrchestrationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(REGION_FILE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RegionChunkReadOrchestrationBehaviour"));
        Assert.assertTrue(text.contains("REGION_CHUNK_READ_ORCHESTRATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("readChunkInputStream("));
        Assert.assertTrue(text.contains("readOutcome.getFailureReason()"));
        Assert.assertTrue(text.contains("readOutcome.getStream()"));
        Assert.assertFalse(text.contains("int l = REGION_CHUNK_LOCATION_BEHAVIOUR.sectorOffset(k);"));
        Assert.assertFalse(text.contains("int i1 = REGION_CHUNK_LOCATION_BEHAVIOUR.sectorCount(k);"));
        Assert.assertFalse(text.contains("this.c.seek((long) (l * 4096));"));
        Assert.assertFalse(text.contains("byte b0 = this.c.readByte();"));
        Assert.assertFalse(text.contains("abyte = new byte[j1 - 1];"));
        Assert.assertFalse(text.contains("REGION_CHUNK_COMPRESSION_BEHAVIOUR.createChunkInputStream(b0, abyte)"));
        Assert.assertTrue(text.contains("RegionChunkWritePlanBehaviour"));
        Assert.assertTrue(text.contains("REGION_CHUNK_WRITE_PLAN_BEHAVIOUR"));
        Assert.assertTrue(text.contains("planWrite("));
        Assert.assertTrue(text.contains("RegionChunkWritePlanBehaviour.WritePlan writePlan"));
        Assert.assertFalse(text.contains("int j1 = REGION_CHUNK_LOCATION_BEHAVIOUR.sectorCount(l);"));
        Assert.assertFalse(text.contains("int l1 = 0;"));
        Assert.assertFalse(text.contains("int i2 = 0;"));
        Assert.assertFalse(text.contains("RegionSectorMapBehaviour.SectorRun sectorRun = REGION_SECTOR_MAP_BEHAVIOUR.findFreeRun(this.f, k1);"));
        Assert.assertFalse(text.contains("REGION_SAVE_STRATEGY_BEHAVIOUR.selectStrategy("));
        Assert.assertTrue(text.contains("RegionChunkWriteCommitBehaviour"));
        Assert.assertTrue(text.contains("REGION_CHUNK_WRITE_COMMIT_BEHAVIOUR"));
        Assert.assertTrue(text.contains("commitWrite("));
        Assert.assertFalse(text.contains("RegionSaveStrategyBehaviour.SaveStrategy saveStrategy = writePlan.getSaveStrategy();"));
        Assert.assertFalse(text.contains("if (saveStrategy == RegionSaveStrategyBehaviour.SaveStrategy.REWRITE)"));
        Assert.assertFalse(text.contains("this.b(i, j, REGION_TIMESTAMP_BEHAVIOUR.currentUnixTimeSeconds());"));
    }
}
