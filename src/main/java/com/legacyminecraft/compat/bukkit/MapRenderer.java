package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map-renderer scaffold.
 */
public abstract class MapRenderer {
    private boolean contextual;

    public boolean isContextual() {
        return contextual;
    }

    public void setContextual(boolean contextual) {
        this.contextual = contextual;
    }

    public void initialize(MapView mapView) {
    }

    public abstract void render(CraftMapView mapView, CraftMapCanvas canvas, Player player);
}
