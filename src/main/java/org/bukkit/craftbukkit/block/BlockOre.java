package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import org.bukkit.craftbukkit.item.Item;
import net.minecraft.server.Material;

import java.util.Random;

public class BlockOre extends net.minecraft.server.CraftBlock {

    public BlockOre(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return this.id == net.minecraft.server.CraftBlock.COAL_ORE.id ? Item.COAL.id : (this.id == net.minecraft.server.CraftBlock.DIAMOND_ORE.id ? Item.DIAMOND.id : (this.id == net.minecraft.server.CraftBlock.LAPIS_ORE.id ? Item.INK_SACK.id : this.id));
    }

    public int a(Random random) {
        return this.id == net.minecraft.server.CraftBlock.LAPIS_ORE.id ? 4 + random.nextInt(5) : 1;
    }

    protected int a_(int i) {
        return this.id == CraftBlock.LAPIS_ORE.id ? 4 : 0;
    }
}
