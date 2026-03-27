package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftHumanEntity inventory-handle bridge creation.
 */
public final class HumanInventoryBridgeBehaviour {
    private static final HumanInventoryBridgeBehaviour INSTANCE = new HumanInventoryBridgeBehaviour();

    private HumanInventoryBridgeBehaviour() {
    }

    public static HumanInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T createInventory(Object entityHuman) {
        Object inventory = BridgeReflection.getField(entityHuman, "inventory");
        try {
            Class<?> wrapperType = Class.forName("org.bukkit.craftbukkit.inventory.CraftInventoryPlayer");
            java.lang.reflect.Constructor<?>[] constructors = wrapperType.getDeclaredConstructors();
            for (java.lang.reflect.Constructor<?> constructor : constructors) {
                if (constructor.getParameterTypes().length == 1) {
                    constructor.setAccessible(true);
                    return BridgeReflection.cast(constructor.newInstance(inventory));
                }
            }
            throw new IllegalStateException("CraftInventoryPlayer constructor not found");
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create CraftInventoryPlayer wrapper", exception);
        }
    }

    public <T> T toInventory(Object craftInventoryPlayer) {
        return BridgeReflection.cast(craftInventoryPlayer);
    }
}
