package com.legacyminecraft.poseidon.world;

/**
 * World-local load event scaffold.
 */
public class WorldLoadEvent {
    private final Object world;

    public WorldLoadEvent(Object world) {
        this.world = world;
    }

    public Object getWorld() {
        return world;
    }
}
