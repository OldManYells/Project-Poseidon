package org.bukkit.craftbukkit.block;

import java.util.Random;

// CraftBukkit start
import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.BlockFadeEvent;
//CraftBukkit end

public class BlockGrass extends net.minecraft.server.CraftBlock {

    public BlockGrass(int i) {
        super(i, Material.GRASS);
        this.textureId = 3;
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            if (world.getLightLevel(i, j + 1, k) < 4 && net.minecraft.server.CraftBlock.q[world.getTypeId(i, j + 1, k)] > 2) {
                if (random.nextInt(4) != 0) {
                    return;
                }

                // CraftBukkit start
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.BlockState blockState = bworld.getBlockAt(i, j, k).getState();
                blockState.setTypeId(net.minecraft.server.CraftBlock.DIRT.id);

                BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
                world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    blockState.update(true);
                }
                // CraftBukkit end
            } else if (world.getLightLevel(i, j + 1, k) >= 9) {
                int l = i + random.nextInt(3) - 1;
                int i1 = j + random.nextInt(5) - 3;
                int j1 = k + random.nextInt(3) - 1;
                int k1 = world.getTypeId(l, i1 + 1, j1);

                if (world.getTypeId(l, i1, j1) == net.minecraft.server.CraftBlock.DIRT.id && world.getLightLevel(l, i1 + 1, j1) >= 4 && net.minecraft.server.CraftBlock.q[k1] <= 2) {
                    // CraftBukkit start
                    org.bukkit.World bworld = world.getWorld();
                    org.bukkit.block.BlockState blockState = bworld.getBlockAt(l, i1, j1).getState();
                    blockState.setTypeId(this.id);

                    BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(i, j, k), blockState);
                    world.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled()) {
                        blockState.update(true);
                    }
                    // CraftBukkit end
                }
            }
        }
    }

    public int a(int i, Random random) {
        return CraftBlock.DIRT.a(0, random);
    }
}
