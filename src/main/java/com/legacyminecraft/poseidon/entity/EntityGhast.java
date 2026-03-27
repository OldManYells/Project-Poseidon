package com.legacyminecraft.poseidon.entity;

import java.util.Random;

/**
 * Entity-local ghast scaffold.
 */
public class EntityGhast extends EntityLiving {
    public int a;
    public double b;
    public double c;
    public double d;
    public int e;
    public int f;

    private final Random random = new Random();
    private Entity targetEntity;
    private int targetRefreshTicks;
    private byte attackWatcher;

    public EntityGhast() {
    }

    public EntityGhast(World world) {
        this.world = world;
    }

    public float poseidonRandomFloat() {
        return random.nextFloat();
    }

    public int poseidonRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public boolean poseidonCanTravelToWaypoint(double waypointX, double waypointY, double waypointZ, double distance) {
        return true;
    }

    public Entity poseidonGetTargetEntity() {
        return targetEntity;
    }

    public void poseidonSetTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public int poseidonGetTargetRefreshTicks() {
        return targetRefreshTicks;
    }

    public void poseidonSetTargetRefreshTicks(int ticks) {
        this.targetRefreshTicks = ticks;
    }

    public float poseidonGetSoundVolume() {
        return 1.0F;
    }

    public Vec3D b(float partialTicks) {
        return new Vec3D(0.0D, 0.0D, 1.0D);
    }

    public byte poseidonGetAttackWatcher() {
        return attackWatcher;
    }

    public void poseidonSetAttackWatcher(byte value) {
        this.attackWatcher = value;
    }
}
