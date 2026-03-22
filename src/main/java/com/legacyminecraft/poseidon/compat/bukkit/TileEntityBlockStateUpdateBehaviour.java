package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.TileEntity;

/**
 * Canonical behaviour for CraftBlockState-derived tile-entity update finalization.
 */
public final class TileEntityBlockStateUpdateBehaviour {
    private static final TileEntityBlockStateUpdateBehaviour INSTANCE = new TileEntityBlockStateUpdateBehaviour();

    private TileEntityBlockStateUpdateBehaviour() {
    }

    public static TileEntityBlockStateUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean finalizeUpdate(boolean parentUpdated, TileEntity tileEntity) {
        if (parentUpdated) {
            tileEntity.update();
        }
        return parentUpdated;
    }
}
