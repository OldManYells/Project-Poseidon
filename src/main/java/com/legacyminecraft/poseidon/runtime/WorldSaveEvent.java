package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local world save event scaffold.
 */
public class WorldSaveEvent {
    private final Object world;

    public WorldSaveEvent(Object world) {
        this.world = world;
    }

    public Object getWorld() {
        return world;
    }
}
