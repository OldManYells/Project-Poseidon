package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;

public class BlockSandStone extends CraftBlock {

    public BlockSandStone(int i) {
        super(i, 192, Material.STONE);
    }

    public int a(int i) {
        return i == 1 ? this.textureId - 16 : (i == 0 ? this.textureId + 16 : this.textureId);
    }
}
