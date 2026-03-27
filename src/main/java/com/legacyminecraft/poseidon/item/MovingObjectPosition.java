package com.legacyminecraft.poseidon.item;

/**
 * Item-local ray-trace hit scaffold.
 */
public class MovingObjectPosition {
    public EnumMovingObjectType type = EnumMovingObjectType.TILE;
    public int b;
    public int c;
    public int d;
    public int face;
    public Vec3D f;

    public MovingObjectPosition(int x, int y, int z, int face, Vec3D hitVector) {
        this.b = x;
        this.c = y;
        this.d = z;
        this.face = face;
        this.f = hitVector;
    }
}
