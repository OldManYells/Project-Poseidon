package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.SheepLifecycleBehaviour;

import java.util.Random;

public class EntitySheep extends EntityAnimal {
    private static final SheepLifecycleBehaviour SHEEP_LIFECYCLE_BEHAVIOUR = SheepLifecycleBehaviour.getInstance();

    public static final float[][] a = SheepLifecycleBehaviour.WOOL_COLORS;

    public EntitySheep(World world) {
        super(world);
        this.texture = "/mob/sheep.png";
        this.b(0.9F, 1.3F);
    }

    protected void b() {
        super.b();
        this.datawatcher.a(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(SHEEP_LIFECYCLE_BEHAVIOUR.createInitialFlags()));
    }

    public boolean damageEntity(Entity entity, int i) {
        return super.damageEntity(entity, i);
    }

    protected void q() {
        SHEEP_LIFECYCLE_BEHAVIOUR.dropDeathLoot(this);
    }

    protected int j() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.getDropBlockId();
    }

    public boolean a(EntityHuman entityhuman) {
        return SHEEP_LIFECYCLE_BEHAVIOUR.tryShear(this, entityhuman, entityhuman.inventory.getItemInHand(), this.random);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Sheared", this.isSheared());
        nbttagcompound.a("Color", (byte) this.getColor());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSheared(nbttagcompound.m("Sheared"));
        this.setColor(nbttagcompound.c("Color"));
    }

    protected String g() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.getDeathSound();
    }

    public int getColor() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.getColor(this.datawatcher.a(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex()));
    }

    public void setColor(int i) {
        byte flags = this.datawatcher.a(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex());
        this.datawatcher.watch(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(SHEEP_LIFECYCLE_BEHAVIOUR.withColor(flags, i)));
    }

    public boolean isSheared() {
        return SHEEP_LIFECYCLE_BEHAVIOUR.isSheared(this.datawatcher.a(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex()));
    }

    public void setSheared(boolean flag) {
        byte flags = this.datawatcher.a(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex());
        this.datawatcher.watch(SHEEP_LIFECYCLE_BEHAVIOUR.getFlagsWatcherIndex(), Byte.valueOf(SHEEP_LIFECYCLE_BEHAVIOUR.withSheared(flags, flag)));
    }

    public static int a(Random random) {
        return SHEEP_LIFECYCLE_BEHAVIOUR.chooseSpawnColor(random);
    }
}
