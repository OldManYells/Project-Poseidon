package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for bridging tile-entity inventories into CraftInventory wrappers.
 */
public final class TileEntityInventoryBridgeBehaviour {
    private static final TileEntityInventoryBridgeBehaviour INSTANCE = new TileEntityInventoryBridgeBehaviour();

    private TileEntityInventoryBridgeBehaviour() {
    }

    public static TileEntityInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T createInventory(Object inventory) {
        if (inventory == null) {
            return null;
        }
        try {
            String inventoryWrapperPackage = resolveInventoryWrapperPackage(inventory.getClass());
            ClassLoader loader = inventory.getClass().getClassLoader();
            Class<?> inventoryWrapperType = loader.loadClass(inventoryWrapperPackage + ".CraftInventory");
            Object wrapper = inventoryWrapperType.getConstructor(Object.class).newInstance(inventory);
            return (T) wrapper;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static String resolveInventoryWrapperPackage(Class<?> handleType) {
        Package handlePackage = handleType.getPackage();
        if (handlePackage == null) {
            return "craftbukkit.inventory";
        }
        String handlePackageName = handlePackage.getName();
        int lastDot = handlePackageName.lastIndexOf('.');
        String basePackage = lastDot >= 0 ? handlePackageName.substring(0, lastDot) : handlePackageName;
        return basePackage + ".craftbukkit.inventory";
    }
}
