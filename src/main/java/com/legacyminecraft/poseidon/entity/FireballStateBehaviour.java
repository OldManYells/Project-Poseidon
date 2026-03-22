package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Vec3D;

public final class FireballStateBehaviour {
    private static final FireballStateBehaviour INSTANCE = new FireballStateBehaviour();

    private FireballStateBehaviour() {
    }

    public static FireballStateBehaviour getInstance() {
        return INSTANCE;
    }

    public DirectionVector computeDirectionFromNoisyVector(double noisyX, double noisyY, double noisyZ) {
        double magnitude = (double) net.minecraft.server.MathHelper.a(noisyX * noisyX + noisyY * noisyY + noisyZ * noisyZ);
        return new DirectionVector(noisyX / magnitude * 0.1D, noisyY / magnitude * 0.1D, noisyZ / magnitude * 0.1D);
    }

    public void writeTileAndGroundState(NBTTagCompound nbt, int tileX, int tileY, int tileZ, int inTileId, int shake, boolean inGround) {
        nbt.a("xTile", (short) tileX);
        nbt.a("yTile", (short) tileY);
        nbt.a("zTile", (short) tileZ);
        nbt.a("inTile", (byte) inTileId);
        nbt.a("shake", (byte) shake);
        nbt.a("inGround", (byte) (inGround ? 1 : 0));
    }

    public LoadedTileState readTileAndGroundState(NBTTagCompound nbt) {
        int tileX = nbt.d("xTile");
        int tileY = nbt.d("yTile");
        int tileZ = nbt.d("zTile");
        int inTileId = nbt.c("inTile") & 255;
        int shake = nbt.c("shake") & 255;
        boolean inGround = nbt.c("inGround") == 1;
        return new LoadedTileState(tileX, tileY, tileZ, inTileId, shake, inGround);
    }

    public boolean onDamagedByEntity(EntityFireball fireball, Entity attacker) {
        if (attacker == null) {
            return false;
        }

        Vec3D attackVector = attacker.Z();
        if (attackVector != null) {
            fireball.motX = attackVector.a;
            fireball.motY = attackVector.b;
            fireball.motZ = attackVector.c;
            fireball.c = fireball.motX * 0.1D;
            fireball.d = fireball.motY * 0.1D;
            fireball.e = fireball.motZ * 0.1D;
        }

        return true;
    }

    public static final class DirectionVector {
        public final double x;
        public final double y;
        public final double z;

        public DirectionVector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class LoadedTileState {
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final int inTileId;
        public final int shake;
        public final boolean inGround;

        public LoadedTileState(int tileX, int tileY, int tileZ, int inTileId, int shake, boolean inGround) {
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.inTileId = inTileId;
            this.shake = shake;
            this.inGround = inGround;
        }
    }
}
