package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat skeleton entity alias.
 */
public class EntitySkeleton extends EntityMonster implements Skeleton {
    public EntitySkeleton() {
    }

    public EntitySkeleton(WorldServer world) {
        this.world = world;
    }
}
