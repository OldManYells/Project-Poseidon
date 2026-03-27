package com.legacyminecraft.poseidon.entity;

import java.util.Random;

/**
 * Entity-local sheep scaffold.
 */
public class EntitySheep extends EntityAnimal {
    private boolean sheared;
    private int color;

    public EntitySheep() {
    }

    public EntitySheep(World world) {
        super(world);
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

    public static int a(Random random) {
        return SheepLifecycleBehaviour.getInstance().chooseSpawnColor(random);
    }
}
