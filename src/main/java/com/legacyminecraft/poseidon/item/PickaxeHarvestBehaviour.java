package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.Material;

public final class PickaxeHarvestBehaviour {
    private static final PickaxeHarvestBehaviour INSTANCE = new PickaxeHarvestBehaviour();

    private PickaxeHarvestBehaviour() {
    }

    public static PickaxeHarvestBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canHarvest(Block block, EnumToolMaterial material) {
        int tier = material.d();
        if (block == Block.OBSIDIAN) {
            return tier == 3;
        }
        if (block == Block.DIAMOND_BLOCK || block == Block.DIAMOND_ORE) {
            return tier >= 2;
        }
        if (block == Block.GOLD_BLOCK || block == Block.GOLD_ORE) {
            return tier >= 2;
        }
        if (block == Block.IRON_BLOCK || block == Block.IRON_ORE) {
            return tier >= 1;
        }
        if (block == Block.LAPIS_BLOCK || block == Block.LAPIS_ORE) {
            return tier >= 1;
        }
        if (block == Block.REDSTONE_ORE || block == Block.GLOWING_REDSTONE_ORE) {
            return tier >= 2;
        }
        return block.material == Material.STONE || block.material == Material.ORE;
    }
}
