package com.legacyminecraft.poseidon.network;

/**
 * Network-local look+move packet scaffold.
 */
public class Packet13PlayerLookMove extends Packet {
    public double x;
    public double y;
    public double stance;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;

    public Packet13PlayerLookMove() {
        this(0.0D, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    }

    public Packet13PlayerLookMove(double x, double y, double stance, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.stance = stance;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}
