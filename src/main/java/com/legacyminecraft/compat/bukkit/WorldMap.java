package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat world-map scaffold.
 */
public class WorldMap extends com.legacyminecraft.poseidon.world.WorldMap {
    public CraftMapView mapView = new CraftMapView();

    public WorldMap() {
        super("compat_map");
    }
}
