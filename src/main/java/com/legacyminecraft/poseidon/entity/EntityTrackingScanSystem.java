package com.legacyminecraft.poseidon.entity;


import java.util.List;
import java.util.Set;

/**
 * Canonical scanner for evaluating tracker membership against one or more viewers.
 */
public final class EntityTrackingScanSystem {
    private static final EntityTrackingScanSystem INSTANCE = new EntityTrackingScanSystem();
    private final EntityTrackingMembershipSystem membershipService = EntityTrackingMembershipSystem.getInstance();

    private EntityTrackingScanSystem() {
    }

    public static EntityTrackingScanSystem getInstance() {
        return INSTANCE;
    }

    public EntityTrackingAttachmentSystem.MotionSnapshot scanPlayer(
            Entity tracker,
            EntityPlayer viewer,
            Set trackedPlayers,
            int trackingDistance,
            int encodedTrackerX,
            int encodedTrackerZ,
            boolean isMoving
    ) {
        EntityTrackingMembershipSystem.MembershipResult membershipResult =
                membershipService.updateMembership(
                        tracker,
                        viewer,
                        trackedPlayers,
                        trackingDistance,
                        encodedTrackerX,
                        encodedTrackerZ,
                        isMoving
                );
        return membershipResult.getMotionSnapshot();
    }

    public EntityTrackingAttachmentSystem.MotionSnapshot scanPlayers(
            Entity tracker,
            List viewers,
            Set trackedPlayers,
            int trackingDistance,
            int encodedTrackerX,
            int encodedTrackerZ,
            boolean isMoving
    ) {
        EntityTrackingAttachmentSystem.MotionSnapshot latestMotionSnapshot = null;
        for (int i = 0; i < viewers.size(); ++i) {
            EntityPlayer viewer = (EntityPlayer) viewers.get(i);
            EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot =
                    scanPlayer(tracker, viewer, trackedPlayers, trackingDistance, encodedTrackerX, encodedTrackerZ, isMoving);
            if (motionSnapshot != null) {
                latestMotionSnapshot = motionSnapshot;
            }
        }
        return latestMotionSnapshot;
    }
}
