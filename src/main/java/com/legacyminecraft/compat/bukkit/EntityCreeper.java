package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat creeper scaffold.
 */
public class EntityCreeper extends EntityMonster implements Creeper {
    private boolean powered;

    public EntityCreeper() {
    }

    public EntityCreeper(WorldServer world) {
        this.world = world;
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }
}
