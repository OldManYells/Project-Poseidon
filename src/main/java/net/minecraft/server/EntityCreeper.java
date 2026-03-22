package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.CreeperPowerBehaviour;
import com.legacyminecraft.poseidon.entity.CreeperFuseBehaviour;
// CraftBukkit start

// CraftBukkit end

public class EntityCreeper extends EntityMonster {
    private static final CreeperPowerBehaviour CREEPER_POWER_BEHAVIOUR = CreeperPowerBehaviour.getInstance();
    private static final CreeperFuseBehaviour CREEPER_FUSE_BEHAVIOUR = CreeperFuseBehaviour.getInstance();

    int fuseTicks;
    int b;

    public EntityCreeper(World world) {
        super(world);
        this.texture = "/mob/creeper.png";
    }

    protected void b() {
        super.b();
        this.datawatcher.a(16, Byte.valueOf(CREEPER_POWER_BEHAVIOUR.createInitialFuseDirectionWatcherValue()));
        this.datawatcher.a(17, Byte.valueOf(CREEPER_POWER_BEHAVIOUR.createInitialPoweredWatcherValue()));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (CREEPER_POWER_BEHAVIOUR.shouldPersistPoweredTag(this.datawatcher.a(17) == 1)) {
            nbttagcompound.a("powered", true);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.watch(17, Byte.valueOf(CREEPER_POWER_BEHAVIOUR.resolvePoweredWatcherValue(CREEPER_POWER_BEHAVIOUR.readPoweredFlag(nbttagcompound.m("powered")))));
    }

    protected void b(Entity entity, float f) {
        CREEPER_FUSE_BEHAVIOUR.tickFuseCooldownWhileIdleServer(this);
    }

    public void m_() {
        CREEPER_FUSE_BEHAVIOUR.tickBeforeSuper(this);
        super.m_();
        CREEPER_FUSE_BEHAVIOUR.tickAfterSuper(this);
    }

    protected String h() {
        return CREEPER_POWER_BEHAVIOUR.getAmbientSound();
    }

    protected String i() {
        return CREEPER_POWER_BEHAVIOUR.getDeathSound();
    }

    public void die(Entity entity) {
        super.die(entity);
        CREEPER_POWER_BEHAVIOUR.dropMusicDiscIfKilledBySkeleton(this, entity, this.random);
    }

    protected void a(Entity entity, float f) {
        CREEPER_FUSE_BEHAVIOUR.handleProximityFuse(this, entity, f);
    }

    public boolean isPowered() {
        return this.datawatcher.a(17) == 1;
    }

    protected int j() {
        return CREEPER_POWER_BEHAVIOUR.getDropItemId();
    }

    private int x() {
        return this.datawatcher.a(16);
    }

    private void e(int i) {
        this.datawatcher.watch(16, Byte.valueOf((byte) i));
    }

    public void a(EntityWeatherStorm entityweatherstorm) {
        super.a(entityweatherstorm);
        CREEPER_POWER_BEHAVIOUR.onLightningStrike(this, entityweatherstorm);
    }

    public void setPowered(boolean powered) {
        this.datawatcher.watch(17, Byte.valueOf(CREEPER_POWER_BEHAVIOUR.resolvePoweredWatcherValue(powered)));
    }

    public int poseidonGetFuseTicks() {
        return this.fuseTicks;
    }

    public void poseidonSetFuseTicks(int fuseTicks) {
        this.fuseTicks = fuseTicks;
    }

    public int poseidonGetLastFuseTicks() {
        return this.b;
    }

    public void poseidonSetLastFuseTicks(int lastFuseTicks) {
        this.b = lastFuseTicks;
    }

    public int poseidonGetFuseDirection() {
        return this.x();
    }

    public void poseidonSetFuseDirection(int fuseDirection) {
        this.e(fuseDirection);
    }

    public void poseidonSetHasActiveAttackGoal(boolean hasActiveAttackGoal) {
        this.e = hasActiveAttackGoal;
    }
}
