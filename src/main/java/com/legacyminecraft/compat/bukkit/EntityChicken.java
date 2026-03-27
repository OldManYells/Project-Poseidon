package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat chicken entity alias.
 */
public class EntityChicken extends EntityAnimal implements Chicken {
    public EntityChicken() {
    }

    public EntityChicken(WorldServer world) {
        this.world = world;
    }
}
