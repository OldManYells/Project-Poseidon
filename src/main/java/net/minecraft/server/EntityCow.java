package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.CowInteractionBehaviour;

public class EntityCow extends EntityAnimal {
    private static final CowInteractionBehaviour COW_INTERACTION_BEHAVIOUR = CowInteractionBehaviour.getInstance();

    public EntityCow(World world) {
        super(world);
        this.texture = "/mob/cow.png";
        this.b(0.9F, 1.3F);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    protected String g() {
        return COW_INTERACTION_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return COW_INTERACTION_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return COW_INTERACTION_BEHAVIOUR.getDeathSound();
    }

    protected float k() {
        return COW_INTERACTION_BEHAVIOUR.getSoundVolume();
    }

    protected int j() {
        return COW_INTERACTION_BEHAVIOUR.getDropItemId();
    }

    public boolean a(EntityHuman entityhuman) {
        return COW_INTERACTION_BEHAVIOUR.tryFillBucket(this, entityhuman, entityhuman.inventory.getItemInHand());
    }
}
