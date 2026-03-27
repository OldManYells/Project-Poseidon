package com.legacyminecraft.compat.bukkit;


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

    public boolean finalizeUpdate(boolean parentUpdated, Object tileEntity) {
        if (parentUpdated) {
            invokeNoArg(tileEntity, "update");
        }
        return parentUpdated;
    }

    private static void invokeNoArg(Object target, String methodName) {
        if (target == null) {
            return;
        }
        try {
            target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
