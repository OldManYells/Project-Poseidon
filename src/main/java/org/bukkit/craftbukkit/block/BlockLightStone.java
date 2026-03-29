package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Item;
import net.minecraft.server.Material;

import java.util.Random;

public class BlockLightStone extends CraftBlock {

    public BlockLightStone(int i, int j, Material material) {
        super(i, j, material);
    }

    public int a(Random random) {
        return 2 + random.nextInt(3);
    }

    public int a(int i, Random random) {
        return Item.GLOWSTONE_DUST.id;
    }
}
