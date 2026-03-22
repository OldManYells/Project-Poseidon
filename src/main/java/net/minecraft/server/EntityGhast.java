package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.GhastCoreBehaviour;
import com.legacyminecraft.poseidon.entity.GhastAiBehaviour;
// CraftBukkit start
// CraftBukkit end

public class EntityGhast extends EntityFlying implements IMonster {
    private static final GhastCoreBehaviour GHAST_CORE_BEHAVIOUR = GhastCoreBehaviour.getInstance();
    private static final GhastAiBehaviour GHAST_AI_BEHAVIOUR = GhastAiBehaviour.getInstance();

    public int a = 0;
    public double b;
    public double c;
    public double d;
    private Entity target = null;
    private int h = 0;
    public int e = 0;
    public int f = 0;

    public EntityGhast(World world) {
        super(world);
        this.texture = "/mob/ghast.png";
        this.b(4.0F, 4.0F);
        this.fireProof = true;
    }

    protected void b() {
        super.b();
        this.datawatcher.a(16, Byte.valueOf(GHAST_CORE_BEHAVIOUR.createInitialAttackWatcher()));
    }

    public void m_() {
        super.m_();
        this.texture = GHAST_CORE_BEHAVIOUR.resolveTexture(this.datawatcher.a(16));
    }

    protected void c_() {
        if (GHAST_CORE_BEHAVIOUR.shouldDespawnWhenMonstersDisabled(this.world.isStatic, this.world.spawnMonsters)) {
            this.die();
        }
        this.U();
        GHAST_AI_BEHAVIOUR.tickAi(this);
    }

    private boolean a(double d0, double d1, double d2, double d3) {
        return GHAST_AI_BEHAVIOUR.canTravelPath(this, d0, d1, d2, d3);
    }

    protected String g() {
        return GHAST_CORE_BEHAVIOUR.getAmbientSound();
    }

    protected String h() {
        return GHAST_CORE_BEHAVIOUR.getHurtSound();
    }

    protected String i() {
        return GHAST_CORE_BEHAVIOUR.getDeathSound();
    }

    protected int j() {
        return GHAST_CORE_BEHAVIOUR.getDropItemId();
    }

    protected float k() {
        return GHAST_CORE_BEHAVIOUR.getSoundVolume();
    }

    public boolean d() {
        return GHAST_CORE_BEHAVIOUR.canSpawn(this.random.nextInt(20), super.d(), this.world.spawnMonsters);
    }

    public int l() {
        return GHAST_CORE_BEHAVIOUR.getMaxClusterSize();
    }

    public boolean poseidonCanTravelToWaypoint(double waypointX, double waypointY, double waypointZ, double distanceToWaypoint) {
        return this.a(waypointX, waypointY, waypointZ, distanceToWaypoint);
    }

    public Entity poseidonGetTargetEntity() {
        return this.target;
    }

    public void poseidonSetTargetEntity(Entity targetEntity) {
        this.target = targetEntity;
    }

    public int poseidonGetTargetRefreshTicks() {
        return this.h;
    }

    public void poseidonSetTargetRefreshTicks(int refreshTicks) {
        this.h = refreshTicks;
    }

    public byte poseidonGetAttackWatcher() {
        return this.datawatcher.a(16);
    }

    public void poseidonSetAttackWatcher(byte attackWatcher) {
        this.datawatcher.watch(16, Byte.valueOf(attackWatcher));
    }

    public float poseidonRandomFloat() {
        return this.random.nextFloat();
    }

    public int poseidonRandomInt(int bound) {
        return this.random.nextInt(bound);
    }

    public float poseidonGetSoundVolume() {
        return this.k();
    }
}
