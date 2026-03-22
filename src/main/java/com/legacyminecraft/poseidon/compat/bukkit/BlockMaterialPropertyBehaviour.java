package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.block.PistonMoveReaction;

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
        return PistonMoveReaction.getById(net.minecraft.server.Block.byId[typeId].material.j());
    }
}
