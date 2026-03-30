package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends net.minecraft.server.CraftBlock {

    public BlockBloodStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    // CraftBukkit start
    public void doPhysics(World world, int i, int j, int k, int l) {
        if (net.minecraft.server.CraftBlock.byId[l] != null && CraftBlock.byId[l].isPowerSource()) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();

            BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(event);
        }
    }
    // CraftBukkit end
}
