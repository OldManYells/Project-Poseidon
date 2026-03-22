package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonPushabilityBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class PistonPushabilityServiceTest {
    @Test
    public void immovableBlockRulesMatchLegacyObsidianAndFurnacePolicy() {
        PistonPushabilityBehaviour service = PistonPushabilityBehaviour.getInstance();

        Assert.assertTrue(service.isAlwaysImmovableBlock(Block.OBSIDIAN.id, true));
        Assert.assertTrue(service.isAlwaysImmovableBlock(Block.FURNACE.id, true));
        Assert.assertTrue(service.isAlwaysImmovableBlock(Block.BURNING_FURNACE.id, true));
        Assert.assertFalse(service.isAlwaysImmovableBlock(Block.FURNACE.id, false));
        Assert.assertFalse(service.isAlwaysImmovableBlock(Block.STONE.id, true));
    }

    @Test
    public void canPushBlockShortCircuitsOnImmovableBlocks() {
        PistonPushabilityBehaviour service = PistonPushabilityBehaviour.getInstance();

        Assert.assertFalse(service.canPushBlock(Block.OBSIDIAN.id, null, 0, 0, 0, true));
        Assert.assertFalse(service.canPushBlock(Block.FURNACE.id, null, 0, 0, 0, true));
    }
}
