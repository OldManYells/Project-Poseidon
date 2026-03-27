package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftBlock world write-path operations.
 */
public final class CraftBlockWriteBehaviour {
    private static final CraftBlockWriteBehaviour INSTANCE = new CraftBlockWriteBehaviour();

    private CraftBlockWriteBehaviour() {
    }

    public static CraftBlockWriteBehaviour getInstance() {
        return INSTANCE;
    }

    public void setData(World world, int x, int y, int z, byte data, boolean applyPhysics) {
        if (applyPhysics) {
            world.setData(x, y, z, data);
        } else {
            world.setRawData(x, y, z, data);
        }
    }

    public boolean setTypeId(World world, int x, int y, int z, int typeId, boolean applyPhysics) {
        if (applyPhysics) {
            return world.setTypeId(x, y, z, typeId);
        }
        return world.setRawTypeId(x, y, z, typeId);
    }

    public boolean setTypeIdAndData(World world, int x, int y, int z, int typeId, byte data, boolean applyPhysics) {
        if (applyPhysics) {
            return world.setTypeIdAndData(x, y, z, typeId, data);
        }
        boolean success = world.setRawTypeIdAndData(x, y, z, typeId, data);
        if (success) {
            world.notify(x, y, z);
        }
        return success;
    }
}
