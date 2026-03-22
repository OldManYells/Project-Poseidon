package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;

public final class ArrowStateBehaviour {
    private static final ArrowStateBehaviour INSTANCE = new ArrowStateBehaviour();

    private ArrowStateBehaviour() {
    }

    public static ArrowStateBehaviour getInstance() {
        return INSTANCE;
    }

    public ShooterLaunchState createShooterLaunchState(double shooterX, double shooterY, double shooterZ, float shooterYaw, float shooterPitch, float shooterEyeHeight) {
        double spawnX = shooterX;
        double spawnY = shooterY + (double) shooterEyeHeight;
        double spawnZ = shooterZ;

        float yawRad = shooterYaw / 180.0F * 3.1415927F;
        float pitchRad = shooterPitch / 180.0F * 3.1415927F;
        spawnX -= (double) (MathHelper.cos(yawRad) * 0.16F);
        spawnY -= 0.10000000149011612D;
        spawnZ -= (double) (MathHelper.sin(yawRad) * 0.16F);

        double baseMotionX = (double) (-MathHelper.sin(yawRad) * MathHelper.cos(pitchRad));
        double baseMotionY = (double) (-MathHelper.sin(pitchRad));
        double baseMotionZ = (double) (MathHelper.cos(yawRad) * MathHelper.cos(pitchRad));

        return new ShooterLaunchState(spawnX, spawnY, spawnZ, baseMotionX, baseMotionY, baseMotionZ);
    }

    public HeadingState computeHeading(double velocityX, double velocityY, double velocityZ, float speed, float spread, double gaussianX, double gaussianY, double gaussianZ) {
        float magnitude = MathHelper.a(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);

        double normX = velocityX / (double) magnitude;
        double normY = velocityY / (double) magnitude;
        double normZ = velocityZ / (double) magnitude;
        normX += gaussianX * 0.007499999832361937D * (double) spread;
        normY += gaussianY * 0.007499999832361937D * (double) spread;
        normZ += gaussianZ * 0.007499999832361937D * (double) spread;
        normX *= (double) speed;
        normY *= (double) speed;
        normZ *= (double) speed;

        float horizontal = MathHelper.a(normX * normX + normZ * normZ);
        float yaw = (float) (Math.atan2(normX, normZ) * 180.0D / 3.1415927410125732D);
        float pitch = (float) (Math.atan2(normY, (double) horizontal) * 180.0D / 3.1415927410125732D);
        return new HeadingState(normX, normY, normZ, yaw, pitch);
    }

    public void writePersistedState(NBTTagCompound nbt, int tileX, int tileY, int tileZ, int inTile, int inData, int shake, boolean inGround, boolean fromPlayer) {
        nbt.a("xTile", (short) tileX);
        nbt.a("yTile", (short) tileY);
        nbt.a("zTile", (short) tileZ);
        nbt.a("inTile", (byte) inTile);
        nbt.a("inData", (byte) inData);
        nbt.a("shake", (byte) shake);
        nbt.a("inGround", (byte) (inGround ? 1 : 0));
        nbt.a("player", fromPlayer);
    }

    public LoadedState readPersistedState(NBTTagCompound nbt) {
        int tileX = nbt.d("xTile");
        int tileY = nbt.d("yTile");
        int tileZ = nbt.d("zTile");
        int inTile = nbt.c("inTile") & 255;
        int inData = nbt.c("inData") & 255;
        int shake = nbt.c("shake") & 255;
        boolean inGround = nbt.c("inGround") == 1;
        boolean fromPlayer = nbt.m("player");
        return new LoadedState(tileX, tileY, tileZ, inTile, inData, shake, inGround, fromPlayer);
    }

    public static final class ShooterLaunchState {
        public final double spawnX;
        public final double spawnY;
        public final double spawnZ;
        public final double baseMotionX;
        public final double baseMotionY;
        public final double baseMotionZ;

        public ShooterLaunchState(double spawnX, double spawnY, double spawnZ, double baseMotionX, double baseMotionY, double baseMotionZ) {
            this.spawnX = spawnX;
            this.spawnY = spawnY;
            this.spawnZ = spawnZ;
            this.baseMotionX = baseMotionX;
            this.baseMotionY = baseMotionY;
            this.baseMotionZ = baseMotionZ;
        }
    }

    public static final class HeadingState {
        public final double motionX;
        public final double motionY;
        public final double motionZ;
        public final float yaw;
        public final float pitch;

        public HeadingState(double motionX, double motionY, double motionZ, float yaw, float pitch) {
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }

    public static final class LoadedState {
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final int inTile;
        public final int inData;
        public final int shake;
        public final boolean inGround;
        public final boolean fromPlayer;

        public LoadedState(int tileX, int tileY, int tileZ, int inTile, int inData, int shake, boolean inGround, boolean fromPlayer) {
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.inTile = inTile;
            this.inData = inData;
            this.shake = shake;
            this.inGround = inGround;
            this.fromPlayer = fromPlayer;
        }
    }
}
