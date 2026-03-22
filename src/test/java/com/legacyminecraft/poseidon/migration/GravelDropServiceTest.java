package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.GravelDropBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class GravelDropServiceTest {
    @Test
    public void flintChanceAndFallbackDropMatchLegacyBehavior() {
        GravelDropBehaviour service = GravelDropBehaviour.getInstance();

        Assert.assertEquals(10, service.flintChanceDivisor());

        Random flintRoll = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        Random gravelRoll = new Random() {
            @Override
            public int nextInt(int bound) {
                return 3;
            }
        };

        Assert.assertEquals(318, service.resolveDropItemId(flintRoll, 13, 318, service.flintChanceDivisor()));
        Assert.assertEquals(13, service.resolveDropItemId(gravelRoll, 13, 318, service.flintChanceDivisor()));
    }
}
