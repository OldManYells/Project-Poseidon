package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.block.Block;
import com.legacyminecraft.poseidon.block.Material;
import com.legacyminecraft.poseidon.block.PistonMoveReaction;

/**
 * Canonical behaviour for CraftBlock material-property query policy.
 */
public final class BlockMaterialPropertyBehaviour {
    private static final BlockMaterialPropertyBehaviour INSTANCE = new BlockMaterialPropertyBehaviour();

    private BlockMaterialPropertyBehaviour() {
    }

    public static BlockMaterialPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isEmpty(Material material) {
        return material == Material.AIR;
    }

    public boolean isLiquid(Material material) {
        return material == Material.WATER
                || material == Material.STATIONARY_WATER
                || material == Material.LAVA
                || material == Material.STATIONARY_LAVA;
    }

    public PistonMoveReaction getPistonMoveReaction(int typeId) {
        if (typeId < 0 || typeId >= Block.byId.length || Block.byId[typeId] == null) {
            return PistonMoveReaction.NORMAL;
        }
        return PistonMoveReaction.getById(Block.byId[typeId].material.j());
    }
}
