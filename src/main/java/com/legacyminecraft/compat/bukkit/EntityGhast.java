package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat ghast entity alias.
 */
public class EntityGhast extends EntityLiving implements Ghast {
    public int a;
    public double b;
    public double c;
    public double d;
    public int e;
    public int f;

    public EntityGhast() {
    }

    public EntityGhast(WorldServer world) {
        this.world = world;
    }
}
