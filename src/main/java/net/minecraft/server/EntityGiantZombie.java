package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.GiantZombieScaleBehaviour;

public class EntityGiantZombie extends EntityMonster {
    private static final GiantZombieScaleBehaviour GIANT_ZOMBIE_SCALE_BEHAVIOUR = GiantZombieScaleBehaviour.getInstance();

    public EntityGiantZombie(World world) {
        super(world);
        this.texture = "/mob/zombie.png";
        this.aE = 0.5F;
        this.damage = GIANT_ZOMBIE_SCALE_BEHAVIOUR.getAttackDamage();
        this.health = GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleHealth(this.health);
        this.height = GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleEyeHeight(this.height);
        this.b(GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleLength(this.length), GIANT_ZOMBIE_SCALE_BEHAVIOUR.scaleWidth(this.width));
    }

    protected float a(int i, int j, int k) {
        return GIANT_ZOMBIE_SCALE_BEHAVIOUR.resolvePathWeight(this.world.n(i, j, k));
    }
}
