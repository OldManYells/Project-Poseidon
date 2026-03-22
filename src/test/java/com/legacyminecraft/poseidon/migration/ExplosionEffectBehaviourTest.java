package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.ExplosionEffectBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ExplosionEffectBehaviourTest {
    private final ExplosionEffectBehaviour behaviour = ExplosionEffectBehaviour.getInstance();

    @Test
    public void computeEntityDamageMatchesLegacyFormula() {
        int damage = behaviour.computeEntityDamage(4.0F, 0.5D);
        Assert.assertEquals(13, damage);
    }

    @Test
    public void shouldIgniteBlockRequiresAirSolidSupportAndRandomHit() {
        Random igniteRandom = new FixedRandom(0);
        Random noIgniteRandom = new FixedRandom(2);

        Assert.assertTrue(behaviour.shouldIgniteBlock(0, 1, igniteRandom));
        Assert.assertFalse(behaviour.shouldIgniteBlock(1, 1, igniteRandom));
        Assert.assertFalse(behaviour.shouldIgniteBlock(0, 0, igniteRandom));
        Assert.assertFalse(behaviour.shouldIgniteBlock(0, 1, noIgniteRandom));
    }

    private static final class FixedRandom extends Random {
        private final int fixedValue;

        private FixedRandom(int fixedValue) {
            this.fixedValue = fixedValue;
        }

        @Override
        public int nextInt(int bound) {
            return fixedValue;
        }
    }
}
