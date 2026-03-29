package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

public class BlockBreakable extends CraftBlock {

    private boolean a;

    protected BlockBreakable(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.a = flag;
    }

    public boolean a() {
        return false;
    }
}
