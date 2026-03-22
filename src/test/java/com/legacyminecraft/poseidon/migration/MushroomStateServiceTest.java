package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.MushroomStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MushroomStateServiceTest {
    @Test
    public void spreadOffsetsAndPlacementRulesMatchLegacyBehavior() {
        MushroomStateBehaviour service = MushroomStateBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertTrue(service.shouldAttemptSpread(deterministic));
        Assert.assertEquals(-1, service.resolveHorizontalOffset(deterministic));
        Assert.assertEquals(0, service.resolveVerticalOffset(deterministic));
        Assert.assertTrue(service.canPlantOn(true));
        Assert.assertFalse(service.canPlantOn(false));
        Assert.assertTrue(service.canStay(64, 128, 12, 13, true));
        Assert.assertFalse(service.canStay(-1, 128, 0, 13, true));
        Assert.assertFalse(service.canStay(128, 128, 0, 13, true));
        Assert.assertFalse(service.canStay(64, 128, 13, 13, true));
        Assert.assertFalse(service.canStay(64, 128, 12, 13, false));
    }
}
