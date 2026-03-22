package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.ExplosionBlockDestructionBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class ExplosionBlockDestructionBehaviourTest {
    private final ExplosionBlockDestructionBehaviour behaviour = ExplosionBlockDestructionBehaviour.getInstance();

    @Test
    public void shouldDestroyBlockSkipsAirAndFire() {
        Assert.assertFalse(behaviour.shouldDestroyBlock(0));
        Assert.assertFalse(behaviour.shouldDestroyBlock(Block.FIRE.id));
        Assert.assertTrue(behaviour.shouldDestroyBlock(Block.STONE.id));
    }
}
