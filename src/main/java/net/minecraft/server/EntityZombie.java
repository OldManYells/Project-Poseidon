package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.ZombieLifecycleBehaviour;

public class EntityZombie extends EntityMonster {
    private static final ZombieLifecycleBehaviour ZOMBIE_LIFECYCLE_BEHAVIOUR = ZombieLifecycleBehaviour.getInstance();

    public EntityZombie(World world) {
        super(world);
        this.texture = "/mob/zombie.png";
        this.aE = 0.5F;
        this.damage = 5;
    }

    public void v() {
        ZOMBIE_LIFECYCLE_BEHAVIOUR.tickSunlightCombustion(this, this.c(1.0F), this.random.nextFloat());
        super.v();
    }

    protected String g() {
        return ZOMBIE_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return ZOMBIE_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return ZOMBIE_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    protected int j() {
        return ZOMBIE_LIFECYCLE_BEHAVIOUR.getDropItemId();
    }
}
