package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

import java.util.Random;

public class BlockStone extends net.minecraft.server.CraftBlock {

    public BlockStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return CraftBlock.COBBLESTONE.id;
    }
}
