package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

public class BlockLeavesBase extends CraftBlock {

    protected boolean b;

    protected BlockLeavesBase(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.b = flag;
    }

    public boolean a() {
        return false;
    }
}
