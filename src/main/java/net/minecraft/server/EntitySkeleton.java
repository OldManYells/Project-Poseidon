package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.entity.SkeletonLifecycleBehaviour;
// CraftBukkit start
// CraftBukkit end

public class EntitySkeleton extends EntityMonster {
    private static final SkeletonLifecycleBehaviour SKELETON_LIFECYCLE_BEHAVIOUR = SkeletonLifecycleBehaviour.getInstance();

    private static final ItemStack a = new ItemStack(Item.BOW, 1);

    public EntitySkeleton(World world) {
        super(world);
        this.texture = "/mob/skeleton.png";
    }

    protected String g() {
        return SKELETON_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return SKELETON_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return SKELETON_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    public void v() {
        SKELETON_LIFECYCLE_BEHAVIOUR.tickSunlightCombustion(this, this.c(1.0F), this.random.nextFloat());
        super.v();
    }

    protected void a(Entity entity, float f) {
        SKELETON_LIFECYCLE_BEHAVIOUR.attackRanged(
                this,
                entity,
                f,
                (boolean) PoseidonConfig.getInstance().getConfigOption("world.settings.skeleton-shooting-sound-fix.enabled", true),
                this.random
        );
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected int j() {
        return SKELETON_LIFECYCLE_BEHAVIOUR.getDropItemId();
    }

    protected void q() {
        SKELETON_LIFECYCLE_BEHAVIOUR.dropDeathLoot(this.world, this.getBukkitEntity(), this.random);
    }

    public void poseidonSetHasActiveAttackGoal(boolean activeAttackGoal) {
        this.e = activeAttackGoal;
    }
}
