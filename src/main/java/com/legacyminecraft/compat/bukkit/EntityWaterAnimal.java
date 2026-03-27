package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat water-animal alias.
 */
public class EntityWaterAnimal extends EntityAnimal implements WaterAnimal {
    public EntityWaterAnimal() {
    }

    public EntityWaterAnimal(WorldServer world) {
        this.world = world;
    }
}
