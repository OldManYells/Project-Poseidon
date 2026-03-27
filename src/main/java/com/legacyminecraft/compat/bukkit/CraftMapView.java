package com.legacyminecraft.compat.bukkit;

/**
 * Canonical CraftMapView scaffold.
 */
public class CraftMapView implements MapView {
    public RenderData render(CraftPlayer player) {
        return new RenderData();
    }
}
