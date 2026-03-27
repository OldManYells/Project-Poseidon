package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat CraftInventory scaffold.
 */
public class CraftInventory implements Inventory {
    private final Object handle;

    public CraftInventory(Object handle) {
        this.handle = handle;
    }

    public CraftInventory(IInventory inventory) {
        this((Object) inventory);
    }

    public CraftInventory(EntityMinecart minecart) {
        this((Object) minecart);
    }

    public Object getHandle() {
        return handle;
    }
}
