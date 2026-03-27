package com.legacyminecraft.poseidon.entity;


import java.util.Set;

/**
 * Canonical behaviour for entity tracker-entry frame decision and sequencing helpers.
 */
public final class EntityTrackingFrameBehaviour {
    private static final EntityTrackingFrameBehaviour INSTANCE = new EntityTrackingFrameBehaviour();

    private EntityTrackingFrameBehaviour() {
    }

    public static EntityTrackingFrameBehaviour getInstance() {
        return INSTANCE;
    }

    public FrameDecision decideFrame(Entity tracker,
                                     EntityTrackingTickPolicy tickPolicy,
                                     boolean hasRescanAnchor,
                                     double anchorX,
                                     double anchorY,
                                     double anchorZ,
                                     int currentTickCounter,
                                     int updateInterval) {
        boolean shouldRescanPlayers = tickPolicy.shouldRescanTrackedPlayers(hasRescanAnchor, tracker, anchorX, anchorY, anchorZ);
        int nextTickCounter = currentTickCounter + 1;
        boolean shouldProcessTrackingFrame = tickPolicy.shouldProcessTrackingFrame(nextTickCounter, updateInterval, tracker);
        return new FrameDecision(nextTickCounter, shouldRescanPlayers, shouldProcessTrackingFrame);
    }

    public RescanAnchor captureRescanAnchor(Entity tracker) {
        return new RescanAnchor(tracker.locX, tracker.locY, tracker.locZ);
    }

    public EntityTrackingState createTrackingState(int encodedX,
                                                   int encodedY,
                                                   int encodedZ,
                                                   int encodedYaw,
                                                   int encodedPitch,
                                                   int teleportCounter,
                                                   double motionX,
                                                   double motionY,
                                                   double motionZ) {
        return new EntityTrackingState(
                encodedX,
                encodedY,
                encodedZ,
                encodedYaw,
                encodedPitch,
                teleportCounter,
                motionX,
                motionY,
                motionZ
        );
    }

    public void processVelocityChangeIfNeeded(boolean velocityChanged,
                                              Entity tracker,
                                              Set trackedPlayers,
                                              EntityVelocityChangeProcessor velocityChangeProcessor,
                                              EntityTrackingDispatchSystem dispatchSystem) {
        if (velocityChanged) {
            velocityChangeProcessor.processVelocityChanged(tracker, trackedPlayers, dispatchSystem);
        }
    }

    public static final class FrameDecision {
        private final int nextTickCounter;
        private final boolean shouldRescanPlayers;
        private final boolean shouldProcessTrackingFrame;

        public FrameDecision(int nextTickCounter, boolean shouldRescanPlayers, boolean shouldProcessTrackingFrame) {
            this.nextTickCounter = nextTickCounter;
            this.shouldRescanPlayers = shouldRescanPlayers;
            this.shouldProcessTrackingFrame = shouldProcessTrackingFrame;
        }

        public int getNextTickCounter() {
            return nextTickCounter;
        }

        public boolean shouldRescanPlayers() {
            return shouldRescanPlayers;
        }

        public boolean shouldProcessTrackingFrame() {
            return shouldProcessTrackingFrame;
        }
    }

    public static final class RescanAnchor {
        private final double x;
        private final double y;
        private final double z;

        public RescanAnchor(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
