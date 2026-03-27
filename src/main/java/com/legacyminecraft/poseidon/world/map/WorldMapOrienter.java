package com.legacyminecraft.poseidon.world.map;

/**
 * Canonical world-map orienter scaffold.
 */
public class WorldMapOrienter {
    private static final WorldMapOrienterBehaviour BEHAVIOUR = WorldMapOrienterBehaviour.getInstance();

    private final WorldMapOrienterBehaviour.OrientationState state;

    public WorldMapOrienter(Object worldMap, byte iconType, byte iconX, byte iconZ, byte iconRotation) {
        this.state = BEHAVIOUR.initialize(worldMap, iconType, iconX, iconZ, iconRotation);
    }

    public Object getWorldMap() {
        return state.worldMap;
    }
}
