package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;

import java.util.Random;

public class BlockObsidian extends BlockStone {

    public BlockObsidian(int i, int j) {
        super(i, j);
    }

    public int a(Random random) {
        return 1;
    }

    public int a(int i, Random random) {
        return CraftBlock.OBSIDIAN.id;
    }
}
