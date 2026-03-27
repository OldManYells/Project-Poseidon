package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat sheep entity alias.
 */
public class EntitySheep extends EntityAnimal implements Sheep {
    private boolean sheared;
    private int color;

    public EntitySheep() {
    }

    public EntitySheep(WorldServer world) {
        this.world = world;
    }

    public boolean isSheared() {
        return sheared;
    }

    public void setSheared(boolean sheared) {
        this.sheared = sheared;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color & 15;
    }
}
