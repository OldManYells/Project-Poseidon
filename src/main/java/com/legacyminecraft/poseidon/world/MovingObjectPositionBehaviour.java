package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;

public final class MovingObjectPositionBehaviour {
    private static final MovingObjectPositionBehaviour INSTANCE = new MovingObjectPositionBehaviour();

    private MovingObjectPositionBehaviour() {
    }

    public static MovingObjectPositionBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeTileHit(MovingObjectPosition target, int x, int y, int z, int face, Vec3D hitVector) {
        target.type = EnumMovingObjectType.TILE;
        target.b = x;
        target.c = y;
        target.d = z;
        target.face = face;
        target.f = Vec3D.create(hitVector.a, hitVector.b, hitVector.c);
    }

    public void initializeEntityHit(MovingObjectPosition target, Entity entity) {
        target.type = EnumMovingObjectType.ENTITY;
        target.entity = entity;
        target.f = Vec3D.create(entity.locX, entity.locY, entity.locZ);
    }
}
