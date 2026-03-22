package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.PigLifecycleBehaviour;

public class EntityPig extends EntityAnimal {
    private static final PigLifecycleBehaviour PIG_LIFECYCLE_BEHAVIOUR = PigLifecycleBehaviour.getInstance();

    public EntityPig(World world) {
        super(world);
        this.texture = "/mob/pig.png";
        this.b(0.9F, 0.9F);
    }

    protected void b() {
        this.datawatcher.a(PIG_LIFECYCLE_BEHAVIOUR.getSaddleWatcherIndex(), Byte.valueOf(PIG_LIFECYCLE_BEHAVIOUR.withSaddleFlag(false)));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Saddle", this.hasSaddle());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSaddle(nbttagcompound.m("Saddle"));
    }

    protected String g() {
        return PIG_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return PIG_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return PIG_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    public boolean a(EntityHuman entityhuman) {
        if (PIG_LIFECYCLE_BEHAVIOUR.canMount(this.hasSaddle(), this.world.isStatic, this.passenger, entityhuman)) {
            entityhuman.mount(this);
            return true;
        } else {
            return false;
        }
    }

    protected int j() {
        return PIG_LIFECYCLE_BEHAVIOUR.getDropItemId(this.fireTicks);
    }

    public boolean hasSaddle() {
        return PIG_LIFECYCLE_BEHAVIOUR.isSaddled(this.datawatcher.a(PIG_LIFECYCLE_BEHAVIOUR.getSaddleWatcherIndex()));
    }

    public void setSaddle(boolean flag) {
        this.datawatcher.watch(PIG_LIFECYCLE_BEHAVIOUR.getSaddleWatcherIndex(), Byte.valueOf(PIG_LIFECYCLE_BEHAVIOUR.withSaddleFlag(flag)));
    }

    public void a(EntityWeatherStorm entityweatherstorm) {
        PIG_LIFECYCLE_BEHAVIOUR.onLightningStrike(this, entityweatherstorm);
    }

    protected void a(float f) {
        super.a(f);
        if (PIG_LIFECYCLE_BEHAVIOUR.shouldAwardPigRideAchievement(f, this.passenger)) {
            PIG_LIFECYCLE_BEHAVIOUR.awardPigRideAchievement(this.passenger);
        }
    }
}
