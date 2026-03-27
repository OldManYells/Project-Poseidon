package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behavior for CraftStorageMinecart inventory wrapping.
 */
public final class StorageMinecartInventoryBridgeBehaviour {
    private static final StorageMinecartInventoryBridgeBehaviour INSTANCE = new StorageMinecartInventoryBridgeBehaviour();

    private StorageMinecartInventoryBridgeBehaviour() {
    }

    public static StorageMinecartInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftInventory createInventory(EntityMinecart minecartEntity) {
        return new CraftInventory(minecartEntity);
    }

    public Inventory toInventory(CraftInventory craftInventory) {
        return craftInventory;
    }
}

