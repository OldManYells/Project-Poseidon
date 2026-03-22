package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.ChickenLifecycleBehaviour;

public class EntityChicken extends EntityAnimal {
    private static final ChickenLifecycleBehaviour CHICKEN_LIFECYCLE_BEHAVIOUR = ChickenLifecycleBehaviour.getInstance();

    public boolean a = false;
    public float b = 0.0F;
    public float c = 0.0F;
    public float f;
    public float g;
    public float h = 1.0F;
    public int i;

    public EntityChicken(World world) {
        super(world);
        this.texture = "/mob/chicken.png";
        this.b(0.3F, 0.4F);
        this.health = 4;
        this.i = CHICKEN_LIFECYCLE_BEHAVIOUR.createInitialEggLayTimer(this.random);
    }

    public void v() {
        super.v();
        ChickenLifecycleBehaviour.ChickenTickState updatedState = CHICKEN_LIFECYCLE_BEHAVIOUR.tick(
                new ChickenLifecycleBehaviour.ChickenTickState(
                        this.b,
                        this.c,
                        this.f,
                        this.g,
                        this.h,
                        this.motY,
                        this.onGround,
                        this.world.isStatic,
                        this.i,
                        this.random
                )
        );

        this.b = updatedState.wingRotation;
        this.c = updatedState.flapSpeed;
        this.f = updatedState.previousFlapSpeed;
        this.g = updatedState.previousWingRotation;
        this.h = updatedState.wingMotionDamping;
        this.motY = updatedState.verticalMotion;
        this.i = updatedState.eggLayTimer;

        if (updatedState.shouldLayEgg) {
            this.world.makeSound(this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.b(Item.EGG.id, 1);
        }
    }

    protected void a(float f) {}

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected String g() {
        return CHICKEN_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return CHICKEN_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return CHICKEN_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    protected int j() {
        return CHICKEN_LIFECYCLE_BEHAVIOUR.getDropItemId();
    }
}
