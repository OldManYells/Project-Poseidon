package com.legacyminecraft.poseidon.world;

/**
 * World-local init event scaffold.
 */
public class WorldInitEvent {
    private final Object world;

    public WorldInitEvent(Object world) {
        this.world = world;
    }

    public Object getWorld() {
        return world;
    }
}
