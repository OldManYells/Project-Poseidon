package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftFurnace inventory and burn/cook state bridge policy.
 */
public final class FurnaceBlockStateBehaviour {
    private static final FurnaceBlockStateBehaviour INSTANCE = new FurnaceBlockStateBehaviour();
    private static final TileEntityInventoryBridgeBehaviour TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR =
            TileEntityInventoryBridgeBehaviour.getInstance();

    private FurnaceBlockStateBehaviour() {
    }

    public static FurnaceBlockStateBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T createInventory(Object furnace) {
        return TILE_ENTITY_INVENTORY_BRIDGE_BEHAVIOUR.createInventory(furnace);
    }

    public boolean finalizeUpdate(boolean parentUpdated, Object furnace) {
        if (parentUpdated) {
            invokeNoArg(furnace, "update");
        }
        return parentUpdated;
    }

    public short getBurnTime(Object furnace) {
        return (short) intField(furnace, "burnTime");
    }

    public void setBurnTime(Object furnace, short burnTime) {
        setIntField(furnace, "burnTime", burnTime);
    }

    public short getCookTime(Object furnace) {
        return (short) intField(furnace, "cookTime");
    }

    public void setCookTime(Object furnace, short cookTime) {
        setIntField(furnace, "cookTime", cookTime);
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

    private static int intField(Object target, String fieldName) {
        if (target == null) {
            return 0;
        }
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            Object value = field.get(target);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private static void setIntField(Object target, String fieldName, int value) {
        if (target == null) {
            return;
        }
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setInt(target, value);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
