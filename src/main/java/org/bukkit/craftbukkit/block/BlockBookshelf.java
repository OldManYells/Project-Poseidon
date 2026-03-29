package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

import java.util.Random;

public class BlockBookshelf extends CraftBlock {

    public BlockBookshelf(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public int a(int i) {
        return i <= 1 ? 4 : this.textureId;
    }

    public int a(Random random) {
        return 0;
    }
}
