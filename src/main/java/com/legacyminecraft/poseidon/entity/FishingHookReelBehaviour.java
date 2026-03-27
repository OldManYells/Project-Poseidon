package com.legacyminecraft.poseidon.entity;


/**
 * Canonical reel physics behaviour for fishing hook retrieval.
 */
public final class FishingHookReelBehaviour {
    private static final FishingHookReelBehaviour INSTANCE = new FishingHookReelBehaviour();

    private FishingHookReelBehaviour() {
    }

    public static FishingHookReelBehaviour getInstance() {
        return INSTANCE;
    }

    public PullMotion computePullMotion(double ownerX, double ownerY, double ownerZ, double hookX, double hookY, double hookZ) {
        double deltaX = ownerX - hookX;
        double deltaY = ownerY - hookY;
        double deltaZ = ownerZ - hookZ;
        double distance = (double) MathHelper.a(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        double pullScale = 0.1D;
        double pullX = deltaX * pullScale;
        double pullY = deltaY * pullScale + (double) MathHelper.a(distance) * 0.08D;
        double pullZ = deltaZ * pullScale;

        return new PullMotion(pullX, pullY, pullZ);
    }

    public static final class PullMotion {
        private final double motX;
        private final double motY;
        private final double motZ;

        public PullMotion(double motX, double motY, double motZ) {
            this.motX = motX;
            this.motY = motY;
            this.motZ = motZ;
        }

        public double getMotX() {
            return motX;
        }

        public double getMotY() {
            return motY;
        }

        public double getMotZ() {
            return motZ;
        }
    }
}
