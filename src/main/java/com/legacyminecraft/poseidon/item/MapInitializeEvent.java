package com.legacyminecraft.poseidon.item;

/**
 * Canonical map initialize event scaffold.
 */
public class MapInitializeEvent {
    private final Object mapView;

    public MapInitializeEvent(Object mapView) {
        this.mapView = mapView;
    }

    public Object getMapView() {
        return mapView;
    }
}
