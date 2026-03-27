package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit map views to CraftMapView wrappers.
 */
public final class MapViewWrapperProjectionBridgeBehaviour {
    private static final MapViewWrapperProjectionBridgeBehaviour INSTANCE = new MapViewWrapperProjectionBridgeBehaviour();

    private MapViewWrapperProjectionBridgeBehaviour() {
    }

    public static MapViewWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftMapView resolveCraftMapView(MapView mapView) {
        return (CraftMapView) mapView;
    }
}
