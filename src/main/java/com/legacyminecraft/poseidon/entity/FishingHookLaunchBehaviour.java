package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.MathHelper;

import java.util.Random;

/**
 * Canonical launch-vector behaviour for fishing hook entities.
 */
public final class FishingHookLaunchBehaviour {
    private static final FishingHookLaunchBehaviour INSTANCE = new FishingHookLaunchBehaviour();

    private FishingHookLaunchBehaviour() {
    }

    public static FishingHookLaunchBehaviour getInstance() {
        return INSTANCE;
    }

    public LaunchState createLaunchState(double x, double y, double z, float velocity, float inaccuracy, Random random) {
        float length = MathHelper.a(x * x + y * y + z * z);

        x /= (double) length;
        y /= (double) length;
        z /= (double) length;
        x += random.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        y += random.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        z += random.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        x *= (double) velocity;
        y *= (double) velocity;
        z *= (double) velocity;
        float horizontalLength = MathHelper.a(x * x + z * z);
        float yaw = (float) (Math.atan2(x, z) * 180.0D / 3.1415927410125732D);
        float pitch = (float) (Math.atan2(y, (double) horizontalLength) * 180.0D / 3.1415927410125732D);

        return new LaunchState(x, y, z, yaw, pitch);
    }

    public int resetTicksInGroundCounter() {
        return 0;
    }

    public static final class LaunchState {
        private final double motX;
        private final double motY;
        private final double motZ;
        private final float yaw;
        private final float pitch;

        public LaunchState(double motX, double motY, double motZ, float yaw, float pitch) {
            this.motX = motX;
            this.motY = motY;
            this.motZ = motZ;
            this.yaw = yaw;
            this.pitch = pitch;
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

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }
}
