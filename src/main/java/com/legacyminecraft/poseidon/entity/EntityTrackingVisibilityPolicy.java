package com.legacyminecraft.poseidon.entity;


import java.util.Set;

/**
 * Canonical policy for entity tracker visibility/range admission decisions.
 */
public final class EntityTrackingVisibilityPolicy {
    private static final EntityTrackingVisibilityPolicy INSTANCE = new EntityTrackingVisibilityPolicy();

    private EntityTrackingVisibilityPolicy() {
    }

    public static EntityTrackingVisibilityPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isWithinTrackingRange(double deltaX, double deltaZ, int trackingDistance) {
        return deltaX >= (double) (-trackingDistance)
                && deltaX <= (double) trackingDistance
                && deltaZ >= (double) (-trackingDistance)
                && deltaZ <= (double) trackingDistance;
    }

    public boolean shouldStartTracking(Set trackedPlayers, EntityPlayer viewer, boolean withinRange, boolean visibleInPlayerChunkMap) {
        return withinRange && !trackedPlayers.contains(viewer) && visibleInPlayerChunkMap;
    }

    public boolean shouldStopTracking(Set trackedPlayers, EntityPlayer viewer, boolean withinRange) {
        return !withinRange && trackedPlayers.contains(viewer);
    }

    public boolean isVisibleInPlayerChunkMap(EntityPlayer viewer, Entity tracker) {
        return viewer.getWorldServer().getPlayerManager().a(viewer, tracker.bH, tracker.bJ);
    }

    public boolean canViewerSeeTracker(Entity tracker, EntityPlayer viewer) {
        if (!(tracker instanceof EntityPlayer)) {
            return true;
        }

        Player trackerBukkitPlayer = (Player) ((EntityPlayer) tracker).getBukkitEntity();
        return ((Player) viewer.getBukkitEntity()).canSee(trackerBukkitPlayer);
    }
}
