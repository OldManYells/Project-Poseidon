package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTileEntityPistonWrapperThinnessTest {
    private static final Path TILE_ENTITY_PISTON_PATH =
            Paths.get("src/main/java/net/minecraft/server/TileEntityPiston.java");

    @Test
    public void tileEntityPistonDelegatesTickAndFinalizationSequencingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(TILE_ENTITY_PISTON_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PistonTileTickOrchestrationBehaviour"));
        Assert.assertTrue(text.contains("PISTON_TILE_TICK_ORCHESTRATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveImmediateFinalization(this.l, PISTON_LIFECYCLE_GATE_BEHAVIOUR)"));
        Assert.assertTrue(text.contains("resolveTickProgression("));
        Assert.assertTrue(text.contains("tickDecision.shouldPushEntities()"));
        Assert.assertTrue(text.contains("tickDecision.shouldFinalize()"));
        Assert.assertFalse(text.contains("if (PISTON_LIFECYCLE_GATE_BEHAVIOUR.shouldFinalizeImmediately(this.l))"));
        Assert.assertFalse(text.contains("if (PISTON_LIFECYCLE_GATE_BEHAVIOUR.shouldFinalizeOnTick(this.l))"));
        Assert.assertFalse(text.contains("this.k = PISTON_TICK_PROGRESSION_BEHAVIOUR.advanceProgress(this.k, 0.5F);"));
        Assert.assertFalse(text.contains("this.a(this.k, PISTON_TICK_PROGRESSION_BEHAVIOUR.computePushDelta(this.k, this.l, 0.0625F));"));
    }
}

