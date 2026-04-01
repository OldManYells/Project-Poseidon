package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.world.block.material.Material;
import com.legacyminecraft.poseidon.world.core.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends Block {

    public BlockBloodStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    // CraftBukkit start
    public void doPhysics(World world, int i, int j, int k, int l) {
        if (com.legacyminecraft.poseidon.world.block.Block.byId[l] != null && com.legacyminecraft.poseidon.world.block.Block.byId[l].isPowerSource()) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();

            BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(event);
        }
    }
    // CraftBukkit end
}
