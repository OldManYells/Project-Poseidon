package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Canonical behaviour for CraftBlockState block update application policy.
 */
public final class BlockStateUpdateBehaviour {
    private static final BlockStateUpdateBehaviour INSTANCE = new BlockStateUpdateBehaviour();

    private BlockStateUpdateBehaviour() {
    }

    public static BlockStateUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean applyUpdate(Block block, Material expectedType, int expectedTypeId, byte rawData, boolean force) {
        synchronized (block) {
            if (block.getType() != expectedType) {
                if (force) {
                    block.setTypeId(expectedTypeId);
                } else {
                    return false;
                }
            }

            block.setData(rawData);
        }

        return true;
    }
}
