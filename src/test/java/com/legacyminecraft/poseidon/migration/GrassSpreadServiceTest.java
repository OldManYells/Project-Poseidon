package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.GrassSpreadBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class GrassSpreadServiceTest {
    @Test
    public void fadeAndSpreadRulesMatchLegacyGrassBehavior() {
        GrassSpreadBehaviour service = GrassSpreadBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertTrue(service.shouldAttemptFade(3, 3));
        Assert.assertFalse(service.shouldAttemptFade(4, 3));
        Assert.assertFalse(service.shouldAttemptFade(3, 2));
        Assert.assertTrue(service.shouldPassFadeRandomGate(deterministic));
        Assert.assertTrue(service.shouldAttemptSpread(9));
        Assert.assertFalse(service.shouldAttemptSpread(8));

        Assert.assertEquals(9, service.resolveSpreadTargetX(10, deterministic));
        Assert.assertEquals(7, service.resolveSpreadTargetY(10, deterministic));
        Assert.assertEquals(9, service.resolveSpreadTargetZ(10, deterministic));

        Assert.assertTrue(service.canSpreadTo(3, 3, 4, 4, 2, 2));
        Assert.assertFalse(service.canSpreadTo(1, 3, 4, 4, 2, 2));
        Assert.assertFalse(service.canSpreadTo(3, 3, 3, 4, 2, 2));
        Assert.assertFalse(service.canSpreadTo(3, 3, 4, 4, 3, 2));
    }
}
