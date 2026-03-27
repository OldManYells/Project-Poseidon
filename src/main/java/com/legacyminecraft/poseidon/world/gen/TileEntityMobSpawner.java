package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation mob-spawner tile-entity scaffold.
 */
public class TileEntityMobSpawner {
    private String mobType = "";

    public void a(String mobType) {
        this.mobType = mobType;
    }

    public String getMobType() {
        return mobType;
    }
}
