package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import org.bukkit.craftbukkit.item.Item;
import net.minecraft.server.Material;

import java.util.Random;

public class BlockClay extends CraftBlock {

    public BlockClay(int i, int j) {
        super(i, j, Material.CLAY);
    }

    public int a(int i, Random random) {
        return Item.CLAY_BALL.id;
    }

    public int a(Random random) {
        return 4;
    }
}
