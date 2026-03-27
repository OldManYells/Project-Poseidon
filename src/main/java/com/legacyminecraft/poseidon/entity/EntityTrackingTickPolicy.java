package com.legacyminecraft.poseidon.entity;


/**
 * Canonical policy for deciding when tracker membership and movement frames should be refreshed.
 */
public final class EntityTrackingTickPolicy {
    private static final EntityTrackingTickPolicy INSTANCE = new EntityTrackingTickPolicy();

    private EntityTrackingTickPolicy() {
    }

    public static EntityTrackingTickPolicy getInstance() {
        return INSTANCE;
    }

    public boolean shouldRescanTrackedPlayers(
            boolean hasRescanAnchor,
            Entity tracker,
            double anchorX,
            double anchorY,
            double anchorZ
    ) {
        return !hasRescanAnchor || tracker.e(anchorX, anchorY, anchorZ) > 16.0D;
    }

    public boolean shouldProcessTrackingFrame(int nextTickCount, int updateInterval, Entity tracker) {
        return nextTickCount % updateInterval == 0 || tracker.airBorne || tracker.aa().a();
    }
}
