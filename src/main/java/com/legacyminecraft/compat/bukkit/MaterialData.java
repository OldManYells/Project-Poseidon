package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat material-data scaffold.
 */
public class MaterialData {
    private final int itemTypeId;
    private final int data;

    public MaterialData(int itemTypeId, int data) {
        this.itemTypeId = itemTypeId;
        this.data = data;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public int getData() {
        return data;
    }
}
