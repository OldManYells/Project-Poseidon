package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat mob-spawner tile-entity scaffold.
 */
public class TileEntityMobSpawner extends TileEntity {
    private String mobType = "";

    public void a(String mobType) {
        this.mobType = mobType;
    }

    public String getMobType() {
        return mobType;
    }
}
