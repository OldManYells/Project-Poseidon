package com.legacyminecraft.poseidon.world;

/**
 * Canonical world-map base scaffold.
 */
public class WorldMapBase {
    public final String id;
    private boolean dirty;

    public WorldMapBase(String id) {
        this.id = id;
    }

    public void a() {
        this.dirty = true;
    }

    public void a(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean b() {
        return this.dirty;
    }
}
