package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.block.Block;
import com.legacyminecraft.poseidon.world.MathHelper;
import com.legacyminecraft.poseidon.world.World;

public final class FlyingMovementBehaviour {
    private static final FlyingMovementBehaviour INSTANCE = new FlyingMovementBehaviour();

    private FlyingMovementBehaviour() {
    }

    public static FlyingMovementBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolveGroundFriction(boolean onGround, World world, double locX, double boundingBoxMinY, double locZ) {
        if (!onGround) {
            return 0.91F;
        }

        float friction = 0.54600006F;
        int blockIdBelow = world.getTypeId(MathHelper.floor(locX), MathHelper.floor(boundingBoxMinY) - 1, MathHelper.floor(locZ));
        if (blockIdBelow > 0) {
            friction = Block.byId[blockIdBelow].frictionFactor * 0.91F;
        }
        return friction;
    }

    public float resolveTravelFactor(boolean onGround, float groundFriction) {
        return onGround ? 0.1F * (0.16277136F / (groundFriction * groundFriction * groundFriction)) : 0.02F;
    }

    public AnimationState updateAnimation(double locX, double locZ, double lastX, double lastZ, float currentAo, float currentAp) {
        double deltaX = locX - lastX;
        double deltaZ = locZ - lastZ;
        float speed = MathHelper.a(deltaX * deltaX + deltaZ * deltaZ) * 4.0F;
        if (speed > 1.0F) {
            speed = 1.0F;
        }

        float newAo = currentAo + (speed - currentAo) * 0.4F;
        float newAp = currentAp + newAo;
        return new AnimationState(newAo, newAp);
    }

    public static final class AnimationState {
        public final float ao;
        public final float ap;

        public AnimationState(float ao, float ap) {
            this.ao = ao;
            this.ap = ap;
        }
    }
}
