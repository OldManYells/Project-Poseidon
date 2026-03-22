package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.RailStateBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class RailStateServiceTest {
    @Test
    public void railIdentityAndCollisionHeightRulesMatchLegacyBehavior() {
        RailStateBehaviour service = RailStateBehaviour.getInstance();

        Assert.assertTrue(service.isRailBlockId(Block.RAILS.id));
        Assert.assertTrue(service.isRailBlockId(Block.GOLDEN_RAIL.id));
        Assert.assertTrue(service.isRailBlockId(Block.DETECTOR_RAIL.id));
        Assert.assertFalse(service.isRailBlockId(Block.STONE.id));

        Assert.assertEquals(0.125F, service.resolveCollisionHeight(1), 0.0F);
        Assert.assertEquals(0.625F, service.resolveCollisionHeight(2), 0.0F);
        Assert.assertEquals(0.625F, service.resolveCollisionHeight(5), 0.0F);
    }

    @Test
    public void textureAndShapeRulesMatchLegacyMetadataEncoding() {
        RailStateBehaviour service = RailStateBehaviour.getInstance();

        Assert.assertEquals(64, service.resolveTextureIndex(true, Block.GOLDEN_RAIL.id, 0, 80, Block.GOLDEN_RAIL.id));
        Assert.assertEquals(80, service.resolveTextureIndex(true, Block.GOLDEN_RAIL.id, 8, 80, Block.GOLDEN_RAIL.id));
        Assert.assertEquals(64, service.resolveTextureIndex(false, Block.RAILS.id, 6, 80, Block.GOLDEN_RAIL.id));
        Assert.assertEquals(80, service.resolveTextureIndex(false, Block.RAILS.id, 5, 80, Block.GOLDEN_RAIL.id));

        Assert.assertEquals(2, service.extractShape(10, true));
        Assert.assertEquals(10, service.extractShape(10, false));
    }

    @Test
    public void supportDropAndPowerToggleRulesMatchLegacyConditions() {
        RailStateBehaviour service = RailStateBehaviour.getInstance();

        Assert.assertTrue(service.shouldDropForMissingSupport(false, 0, true, true, true, true));
        Assert.assertTrue(service.shouldDropForMissingSupport(true, 2, false, true, true, true));
        Assert.assertFalse(service.shouldDropForMissingSupport(true, 2, true, true, true, true));

        Assert.assertTrue(service.shouldNotifyBlockAboveOnPoweredStateChange(2));
        Assert.assertFalse(service.shouldNotifyBlockAboveOnPoweredStateChange(1));
        Assert.assertEquals(10, service.setPoweredState(2, true));
        Assert.assertEquals(2, service.setPoweredState(2, false));
        Assert.assertTrue(service.isPowered(8));
        Assert.assertFalse(service.isPowered(2));
        Assert.assertTrue(service.canPlace(true));
        Assert.assertFalse(service.canPlace(false));
    }

    @Test
    public void propagationStepAndCompatibilityRulesMatchLegacySwitchBehavior() {
        RailStateBehaviour service = RailStateBehaviour.getInstance();

        RailStateBehaviour.PropagationStep flatNorth = service.computePropagationStep(10, 64, 10, 0, true);
        Assert.assertEquals(11, flatNorth.getNextZ());
        Assert.assertEquals(10, flatNorth.getNextX());
        Assert.assertTrue(flatNorth.shouldCheckBelowFallback());
        Assert.assertEquals(0, flatNorth.getExpectedAxis());

        RailStateBehaviour.PropagationStep ascendingEast = service.computePropagationStep(10, 64, 10, 2, false);
        Assert.assertEquals(11, ascendingEast.getNextX());
        Assert.assertEquals(65, ascendingEast.getNextY());
        Assert.assertFalse(ascendingEast.shouldCheckBelowFallback());
        Assert.assertEquals(1, ascendingEast.getExpectedAxis());

        Assert.assertFalse(service.isPropagationShapeCompatible(1, 0));
        Assert.assertTrue(service.isPropagationShapeCompatible(1, 1));
        Assert.assertFalse(service.isPropagationShapeCompatible(0, 2));
        Assert.assertTrue(service.isPropagationShapeCompatible(0, 4));
        Assert.assertTrue(service.canContinuePropagation(0));
        Assert.assertFalse(service.canContinuePropagation(8));
    }
}
