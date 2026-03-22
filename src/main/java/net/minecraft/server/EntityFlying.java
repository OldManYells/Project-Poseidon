package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.FlyingMovementBehaviour;

public class EntityFlying extends EntityLiving {
    private static final FlyingMovementBehaviour FLYING_MOVEMENT_BEHAVIOUR = FlyingMovementBehaviour.getInstance();

    public EntityFlying(World world) {
        super(world);
    }

    protected void a(float f) {}

    public void a(float f, float f1) {
        if (this.ad()) {
            this.a(f, f1, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929D;
            this.motY *= 0.800000011920929D;
            this.motZ *= 0.800000011920929D;
        } else if (this.ae()) {
            this.a(f, f1, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        } else {
            float f2 = FLYING_MOVEMENT_BEHAVIOUR.resolveGroundFriction(this.onGround, this.world, this.locX, this.boundingBox.b, this.locZ);
            this.a(f, f1, FLYING_MOVEMENT_BEHAVIOUR.resolveTravelFactor(this.onGround, f2));
            f2 = FLYING_MOVEMENT_BEHAVIOUR.resolveGroundFriction(this.onGround, this.world, this.locX, this.boundingBox.b, this.locZ);

            this.move(this.motX, this.motY, this.motZ);
            this.motX *= (double) f2;
            this.motY *= (double) f2;
            this.motZ *= (double) f2;
        }

        this.an = this.ao;
        FlyingMovementBehaviour.AnimationState animationState = FLYING_MOVEMENT_BEHAVIOUR.updateAnimation(this.locX, this.locZ, this.lastX, this.lastZ, this.ao, this.ap);
        this.ao = animationState.ao;
        this.ap = animationState.ap;
    }

    public boolean p() {
        return false;
    }
}
