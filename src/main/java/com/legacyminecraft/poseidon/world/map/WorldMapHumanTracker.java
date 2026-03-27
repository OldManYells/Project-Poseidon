package com.legacyminecraft.poseidon.world.map;

/**
 * Canonical world-map tracker scaffold.
 */
public class WorldMapHumanTracker {
    private static final WorldMapHumanTrackerBehaviour BEHAVIOUR = WorldMapHumanTrackerBehaviour.getInstance();

    public final Object trackee;
    public final int[] b = new int[128];
    public final int[] c = new int[128];
    private final Object worldMap;
    private int updateCursor;
    private int markerCooldown = 4;
    private byte[] lastMarkerPacket;

    public WorldMapHumanTracker(Object worldMap, Object trackee) {
        this.worldMap = worldMap;
        this.trackee = trackee;
        BEHAVIOUR.initializeBounds(this.b, this.c);
    }

    public byte[] a(Object itemstack) {
        WorldMapHumanTrackerBehaviour.UpdateState state =
                BEHAVIOUR.createUpdate(this.worldMap, this.trackee, itemstack, this.b, this.c, this.updateCursor, this.markerCooldown, this.lastMarkerPacket);
        this.updateCursor = state.updateCursor;
        this.markerCooldown = state.markerCooldown;
        this.lastMarkerPacket = state.lastMarkerPacket;
        return state.payload;
    }
}
