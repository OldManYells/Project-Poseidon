package com.legacyminecraft.poseidon.entity;


import java.util.Set;

/**
 * Canonical coordinator for adding/removing viewers from tracker membership.
 */
public final class EntityTrackingMembershipSystem {
    private static final EntityTrackingMembershipSystem INSTANCE = new EntityTrackingMembershipSystem();
    private final EntityTrackingVisibilityPolicy visibilityPolicy = EntityTrackingVisibilityPolicy.getInstance();
    private final EntitySpawnPacketFactory spawnPacketFactory = EntitySpawnPacketFactory.getInstance();
    private final EntityTrackingAttachmentSystem attachmentService = EntityTrackingAttachmentSystem.getInstance();

    private EntityTrackingMembershipSystem() {
    }

    public static EntityTrackingMembershipSystem getInstance() {
        return INSTANCE;
    }

    public MembershipResult updateMembership(
            Entity tracker,
            EntityPlayer viewer,
            Set trackedPlayers,
            int trackingDistance,
            int encodedTrackerX,
            int encodedTrackerZ,
            boolean isMoving
    ) {
        if (isSelfTracking(tracker, viewer)) {
            return MembershipResult.none();
        }

        double deltaX = viewer.locX - (double) (encodedTrackerX / 32);
        double deltaZ = viewer.locZ - (double) (encodedTrackerZ / 32);

        boolean withinTrackingRange = visibilityPolicy.isWithinTrackingRange(deltaX, deltaZ, trackingDistance);
        boolean visibleInPlayerChunkMap = visibilityPolicy.isVisibleInPlayerChunkMap(viewer, tracker);

        if (visibilityPolicy.shouldStartTracking(trackedPlayers, viewer, withinTrackingRange, visibleInPlayerChunkMap)) {
            if (!visibilityPolicy.canViewerSeeTracker(tracker, viewer)) {
                return MembershipResult.none();
            }

            viewer.removeQueue.remove(Integer.valueOf(tracker.id));
            trackedPlayers.add(viewer);
            Packet packet = spawnPacketFactory.createSpawnPacket(tracker);
            viewer.netServerHandler.sendPacket(packet);

            EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot =
                    attachmentService.sendInitialTrackingPackets(tracker, viewer, isMoving);
            return MembershipResult.started(motionSnapshot);
        }

        if (visibilityPolicy.shouldStopTracking(trackedPlayers, viewer, withinTrackingRange)) {
            trackedPlayers.remove(viewer);
            viewer.removeQueue.add(Integer.valueOf(tracker.id));
            return MembershipResult.stopped();
        }

        return MembershipResult.none();
    }

    public boolean isSelfTracking(Entity tracker, EntityPlayer viewer) {
        return viewer == tracker;
    }

    public static final class MembershipResult {
        private final boolean startedTracking;
        private final boolean stoppedTracking;
        private final EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot;

        private MembershipResult(boolean startedTracking, boolean stoppedTracking, EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot) {
            this.startedTracking = startedTracking;
            this.stoppedTracking = stoppedTracking;
            this.motionSnapshot = motionSnapshot;
        }

        public static MembershipResult none() {
            return new MembershipResult(false, false, null);
        }

        public static MembershipResult stopped() {
            return new MembershipResult(false, true, null);
        }

        public static MembershipResult started(EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot) {
            return new MembershipResult(true, false, motionSnapshot);
        }

        public boolean isStartedTracking() {
            return startedTracking;
        }

        public boolean isStoppedTracking() {
            return stoppedTracking;
        }

        public EntityTrackingAttachmentSystem.MotionSnapshot getMotionSnapshot() {
            return motionSnapshot;
        }
    }
}
