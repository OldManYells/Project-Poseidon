package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.JukeboxStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class JukeboxStateServiceTest {
    @Test
    public void textureRecordAndInteractionRulesMatchLegacyJukeboxBehavior() {
        JukeboxStateBehaviour service = JukeboxStateBehaviour.getInstance();

        Assert.assertEquals(84, service.resolveTextureBySide(1, 83));
        Assert.assertEquals(83, service.resolveTextureBySide(2, 83));
        Assert.assertTrue(service.hasRecord(1));
        Assert.assertFalse(service.hasRecord(0));
        Assert.assertTrue(service.shouldIgnoreClientOperations(true));
        Assert.assertFalse(service.shouldIgnoreClientOperations(false));
        Assert.assertTrue(service.shouldEjectRecord(2256));
        Assert.assertFalse(service.shouldEjectRecord(0));
        Assert.assertEquals(0, service.clearedRecordItemId());
        Assert.assertEquals(1, service.insertedBlockData());
        Assert.assertEquals(1005, service.stoppedRecordEffectId());
        Assert.assertEquals(10, service.recordPickupDelayTicks());
    }

    @Test
    public void recordDropOffsetRulesMatchLegacyJukeboxBehavior() {
        JukeboxStateBehaviour service = JukeboxStateBehaviour.getInstance();
        Random seeded = new Random(6L);
        float spread = service.recordDropSpread();

        double x = service.resolveDropXOffset(seeded, spread);
        double y = service.resolveDropYOffset(seeded, spread);
        double z = service.resolveDropZOffset(seeded, spread);
        Assert.assertTrue(x >= 0.15D && x <= 0.85D);
        Assert.assertTrue(y >= 0.66D && y <= 1.36D);
        Assert.assertTrue(z >= 0.15D && z <= 0.85D);
    }
}
