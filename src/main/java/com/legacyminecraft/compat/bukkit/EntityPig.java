package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat pig entity alias.
 */
public class EntityPig extends EntityAnimal implements Pig {
    public boolean hasSaddle;

    public EntityPig() {
    }

    public EntityPig(WorldServer world) {
        this.world = world;
    }

    public void setSaddle(boolean saddled) {
        this.hasSaddle = saddled;
    }

    public boolean hasSaddle() {
        return hasSaddle;
    }
}
