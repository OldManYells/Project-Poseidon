package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.world.MovingObjectPositionBehaviour;

public class MovingObjectPosition {
    private static final MovingObjectPositionBehaviour MOVING_OBJECT_POSITION_BEHAVIOUR = MovingObjectPositionBehaviour.getInstance();

    public EnumMovingObjectType type;
    public int b;
    public int c;
    public int d;
    public int face;
    public Vec3D f;
    public Entity entity;

    public MovingObjectPosition(int i, int j, int k, int l, Vec3D vec3d) {
        MOVING_OBJECT_POSITION_BEHAVIOUR.initializeTileHit(this, i, j, k, l, vec3d);
    }

    public MovingObjectPosition(Entity entity) {
        MOVING_OBJECT_POSITION_BEHAVIOUR.initializeEntityHit(this, entity);
    }
}
