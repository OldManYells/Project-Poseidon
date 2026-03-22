package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.SquidLifecycleBehaviour;

public class EntitySquid extends EntityWaterAnimal {
    private static final SquidLifecycleBehaviour SQUID_LIFECYCLE_BEHAVIOUR = SquidLifecycleBehaviour.getInstance();

    public float a = 0.0F;
    public float b = 0.0F;
    public float c = 0.0F;
    public float f = 0.0F;
    public float g = 0.0F;
    public float h = 0.0F;
    public float i = 0.0F;
    public float j = 0.0F;
    private float k = 0.0F;
    private float l = 0.0F;
    private float m = 0.0F;
    private float n = 0.0F;
    private float o = 0.0F;
    private float p = 0.0F;

    public EntitySquid(World world) {
        super(world);
        this.texture = "/mob/squid.png";
        this.b(0.95F, 0.95F);
        this.l = SQUID_LIFECYCLE_BEHAVIOUR.createInitialTentacleSpeed(this.random);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected String g() {
        return SQUID_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return SQUID_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return SQUID_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    protected float k() {
        return SQUID_LIFECYCLE_BEHAVIOUR.getSoundVolume();
    }

    protected int j() {
        return SQUID_LIFECYCLE_BEHAVIOUR.getDropItemId();
    }

    protected void q() {
        SQUID_LIFECYCLE_BEHAVIOUR.dropDeathLoot(this.world, this.getBukkitEntity(), this.random);
    }

    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    public boolean ad() {
        return SQUID_LIFECYCLE_BEHAVIOUR.isInWater(this.world, this.boundingBox, this);
    }

    public void v() {
        super.v();
        SquidLifecycleBehaviour.SquidMotionState updatedMotion = SQUID_LIFECYCLE_BEHAVIOUR.tick(
                new SquidLifecycleBehaviour.SquidMotionState(
                        this.a,
                        this.b,
                        this.c,
                        this.f,
                        this.g,
                        this.h,
                        this.k,
                        this.j,
                        this.i,
                        this.l,
                        this.m,
                        this.n,
                        this.o,
                        this.p,
                        this.motX,
                        this.motY,
                        this.motZ,
                        this.K,
                        this.yaw,
                        this.ad(),
                        this.Y,
                        this.random
                )
        );

        this.a = updatedMotion.pitch;
        this.b = updatedMotion.previousPitch;
        this.c = updatedMotion.bodyYaw;
        this.f = updatedMotion.previousBodyYaw;
        this.g = updatedMotion.tentacleAngle;
        this.h = updatedMotion.previousTentacleAngle;
        this.k = updatedMotion.swimVelocity;
        this.j = updatedMotion.previousRotationVelocity;
        this.i = updatedMotion.rotationVelocity;
        this.l = updatedMotion.tentacleSpeed;
        this.m = updatedMotion.bodyBob;
        this.motX = updatedMotion.motionX;
        this.motY = updatedMotion.motionY;
        this.motZ = updatedMotion.motionZ;
        this.K = updatedMotion.renderYawOffset;
        this.yaw = updatedMotion.yaw;
        this.n = updatedMotion.swimDirectionX;
        this.o = updatedMotion.swimDirectionY;
        this.p = updatedMotion.swimDirectionZ;
    }

    public void a(float f, float f1) {
        this.move(this.motX, this.motY, this.motZ);
    }

    protected void c_() {
        if (SQUID_LIFECYCLE_BEHAVIOUR.shouldRetargetSwimDirection(this.random.nextInt(50), this.bA, this.n, this.o, this.p)) {
            SquidLifecycleBehaviour.SwimDirection direction = SQUID_LIFECYCLE_BEHAVIOUR.randomSwimDirection(this.random);
            this.n = direction.x;
            this.o = direction.y;
            this.p = direction.z;
        }

        this.U();
    }
}
