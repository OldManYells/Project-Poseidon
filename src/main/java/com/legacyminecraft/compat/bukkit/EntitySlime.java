package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat slime entity alias.
 */
public class EntitySlime extends EntityLiving implements IAnimal, Slime {
    private int size = 1;

    public EntitySlime() {
    }

    public EntitySlime(WorldServer world) {
        this.world = world;
    }

    public void setSize(int size) {
        this.size = Math.max(1, size);
    }

    public int getSize() {
        return size;
    }
}
