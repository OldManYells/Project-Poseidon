package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPistonBlockWrapperThinnessTest {
    private static final Path BLOCK_PISTON_PATH = Paths.get("src/main/java/net/minecraft/server/BlockPiston.java");

    @Test
    public void blockPistonDelegatesMovementChainFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(BLOCK_PISTON_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PistonMovementChainBehaviour"));
        Assert.assertTrue(text.contains("PistonEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("PistonPowerTransitionBehaviour"));
        Assert.assertTrue(text.contains("PistonStickyRetractionBehaviour"));
        Assert.assertTrue(text.contains("PISTON_MOVEMENT_CHAIN_BEHAVIOUR.calculateExtensionLength"));
        Assert.assertTrue(text.contains("PISTON_MOVEMENT_CHAIN_BEHAVIOUR.extendWithMovingBlocks"));
        Assert.assertTrue(text.contains("PISTON_EVENT_BRIDGE_BEHAVIOUR.isExtendAllowed"));
        Assert.assertTrue(text.contains("PISTON_EVENT_BRIDGE_BEHAVIOUR.isRetractAllowed"));
        Assert.assertTrue(text.contains("PISTON_POWER_TRANSITION_BEHAVIOUR.resolveTransition"));
        Assert.assertTrue(text.contains("PISTON_POWER_TRANSITION_BEHAVIOUR.composeExtendedData"));
        Assert.assertTrue(text.contains("PISTON_POWER_TRANSITION_BEHAVIOUR.composeRetractedData"));
        Assert.assertTrue(text.contains("PISTON_STICKY_RETRACTION_BEHAVIOUR.resolveRetractionPositions"));
        Assert.assertTrue(text.contains("PISTON_STICKY_RETRACTION_BEHAVIOUR.resolvePulledBlockState"));
        Assert.assertTrue(text.contains("PISTON_STICKY_RETRACTION_BEHAVIOUR.shouldPullBlock"));
        Assert.assertTrue(text.contains("PISTON_STICKY_RETRACTION_BEHAVIOUR.shouldClearAdjacentBlock"));
        Assert.assertTrue(text.contains("MOVEMENT_CHAIN_PUSHABILITY_QUERY"));
        Assert.assertTrue(text.contains("STICKY_RETRACTION_PUSHABILITY_QUERY"));
        Assert.assertTrue(text.contains("RAW_AIR_CLEAR_ACTION"));
        Assert.assertTrue(text.contains("TYPE_AIR_CLEAR_ACTION"));
        Assert.assertFalse(text.contains("while (true) {"));
        Assert.assertFalse(text.contains("while (i1 != i || j1 != j || k1 != k)"));
        Assert.assertFalse(text.contains("new BlockPistonExtendEvent"));
        Assert.assertFalse(text.contains("new BlockPistonRetractEvent"));
        Assert.assertFalse(text.contains("if (l != 7) {"));
        Assert.assertFalse(text.contains("if (flag && !d(l)) {"));
        Assert.assertFalse(text.contains("if (i2 == Block.PISTON_MOVING.id)"));
        Assert.assertFalse(text.contains("if (useRawAirClear) {"));
    }
}
