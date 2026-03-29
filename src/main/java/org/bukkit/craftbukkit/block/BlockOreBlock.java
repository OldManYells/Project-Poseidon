package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

public class BlockOreBlock extends CraftBlock {

    public BlockOreBlock(int i, int j) {
        super(i, Material.ORE);
        this.textureId = j;
    }

    public int a(int i) {
        return this.textureId;
    }
}
