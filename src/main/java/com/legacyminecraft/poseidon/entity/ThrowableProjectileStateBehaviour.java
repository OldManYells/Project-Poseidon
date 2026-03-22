package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;

public final class ThrowableProjectileStateBehaviour {
    private static final ThrowableProjectileStateBehaviour INSTANCE = new ThrowableProjectileStateBehaviour();

    private ThrowableProjectileStateBehaviour() {
    }

    public static ThrowableProjectileStateBehaviour getInstance() {
        return INSTANCE;
    }

    public ArrowStateBehaviour.ShooterLaunchState createShooterLaunchState(double shooterX, double shooterY, double shooterZ, float shooterYaw, float shooterPitch, float shooterEyeHeight, float baseSpeed) {
        double spawnX = shooterX;
        double spawnY = shooterY + (double) shooterEyeHeight;
        double spawnZ = shooterZ;

        float yawRad = shooterYaw / 180.0F * 3.1415927F;
        float pitchRad = shooterPitch / 180.0F * 3.1415927F;
        spawnX -= (double) (MathHelper.cos(yawRad) * 0.16F);
        spawnY -= 0.10000000149011612D;
        spawnZ -= (double) (MathHelper.sin(yawRad) * 0.16F);

        double baseMotionX = (double) (-MathHelper.sin(yawRad) * MathHelper.cos(pitchRad) * baseSpeed);
        double baseMotionY = (double) (-MathHelper.sin(pitchRad) * baseSpeed);
        double baseMotionZ = (double) (MathHelper.cos(yawRad) * MathHelper.cos(pitchRad) * baseSpeed);
        return new ArrowStateBehaviour.ShooterLaunchState(spawnX, spawnY, spawnZ, baseMotionX, baseMotionY, baseMotionZ);
    }

    public ArrowStateBehaviour.HeadingState computeHeading(double velocityX, double velocityY, double velocityZ, float speed, float spread, double gaussianX, double gaussianY, double gaussianZ) {
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
        return new ArrowStateBehaviour.HeadingState(normX, normY, normZ, yaw, pitch);
    }

    public void writePersistedState(NBTTagCompound nbt, int tileX, int tileY, int tileZ, int inTile, int shake, boolean inGround) {
        nbt.a("xTile", (short) tileX);
        nbt.a("yTile", (short) tileY);
        nbt.a("zTile", (short) tileZ);
        nbt.a("inTile", (byte) inTile);
        nbt.a("shake", (byte) shake);
        nbt.a("inGround", (byte) (inGround ? 1 : 0));
    }

    public LoadedState readPersistedState(NBTTagCompound nbt) {
        int tileX = nbt.d("xTile");
        int tileY = nbt.d("yTile");
        int tileZ = nbt.d("zTile");
        int inTile = nbt.c("inTile") & 255;
        int shake = nbt.c("shake") & 255;
        boolean inGround = nbt.c("inGround") == 1;
        return new LoadedState(tileX, tileY, tileZ, inTile, shake, inGround);
    }

    public static final class LoadedState {
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final int inTile;
        public final int shake;
        public final boolean inGround;

        public LoadedState(int tileX, int tileY, int tileZ, int inTile, int shake, boolean inGround) {
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.inTile = inTile;
            this.shake = shake;
            this.inGround = inGround;
        }
    }
}
