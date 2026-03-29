package org.bukkit.craftbukkit.item;


import net.minecraft.server.CraftBlock;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.Material;

public class ItemPickaxe extends ItemTool {

    private static CraftBlock[] bk = new CraftBlock[] { CraftBlock.COBBLESTONE, CraftBlock.DOUBLE_STEP, CraftBlock.STEP, CraftBlock.STONE, CraftBlock.SANDSTONE, CraftBlock.MOSSY_COBBLESTONE, CraftBlock.IRON_ORE, CraftBlock.IRON_BLOCK, CraftBlock.COAL_ORE, CraftBlock.GOLD_BLOCK, CraftBlock.GOLD_ORE, CraftBlock.DIAMOND_ORE, CraftBlock.DIAMOND_BLOCK, CraftBlock.ICE, CraftBlock.NETHERRACK, CraftBlock.LAPIS_ORE, CraftBlock.LAPIS_BLOCK};

    protected ItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, bk);
    }

    public boolean a(CraftBlock baseBlock) {
        return baseBlock == CraftBlock.OBSIDIAN ? this.a.d() == 3 : (baseBlock != CraftBlock.DIAMOND_BLOCK && baseBlock != CraftBlock.DIAMOND_ORE ? (baseBlock != CraftBlock.GOLD_BLOCK && baseBlock != CraftBlock.GOLD_ORE ? (baseBlock != CraftBlock.IRON_BLOCK && baseBlock != CraftBlock.IRON_ORE ? (baseBlock != CraftBlock.LAPIS_BLOCK && baseBlock != CraftBlock.LAPIS_ORE ? (baseBlock != CraftBlock.REDSTONE_ORE && baseBlock != CraftBlock.GLOWING_REDSTONE_ORE ? (baseBlock.material == Material.STONE ? true : baseBlock.material == Material.ORE) : this.a.d() >= 2) : this.a.d() >= 1) : this.a.d() >= 1) : this.a.d() >= 2) : this.a.d() >= 2);
    }
}
