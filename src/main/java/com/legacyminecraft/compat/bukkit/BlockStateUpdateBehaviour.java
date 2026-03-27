package com.legacyminecraft.compat.bukkit;


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

    public boolean applyUpdate(Object block, Object expectedType, int expectedTypeId, byte rawData, boolean force) {
        synchronized (block) {
            Object currentType = BridgeReflection.invoke(block, "getType");
            if (!sameValue(currentType, expectedType)) {
                if (force) {
                    BridgeReflection.invoke(block, "setTypeId", Integer.valueOf(expectedTypeId));
                } else {
                    return false;
                }
            }

            BridgeReflection.invoke(block, "setData", Byte.valueOf(rawData));
        }

        return true;
    }

    private boolean sameValue(Object left, Object right) {
        return left == right || (left != null && left.equals(right));
    }
}
