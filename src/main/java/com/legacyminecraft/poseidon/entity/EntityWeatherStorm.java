package com.legacyminecraft.poseidon.entity;

import java.util.Random;

/**
 * Entity-local lightning storm scaffold.
 */
public class EntityWeatherStorm extends EntityWeather {
    public boolean isEffect;
    public long a;

    private final Random random = new Random();
    private int lifeTicks;
    private int flashCount;

    public EntityWeatherStorm() {
    }

    public EntityWeatherStorm(World world, double x, double y, double z) {
        this.world = world;
        setPositionRotation(x, y, z, 0.0F, 0.0F);
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void poseidonSetLifeTicks(int lifeTicks) {
        this.lifeTicks = lifeTicks;
    }

    public int poseidonGetLifeTicks() {
        return lifeTicks;
    }

    public void poseidonSetFlashCount(int flashCount) {
        this.flashCount = flashCount;
    }

    public int poseidonGetFlashCount() {
        return flashCount;
    }

    public long poseidonNextRandomLong() {
        return random.nextLong();
    }

    public int poseidonNextRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public float poseidonNextRandomFloat() {
        return random.nextFloat();
    }

    public com.legacyminecraft.compat.bukkit.World poseidonGetCraftWorld() {
        return new com.legacyminecraft.compat.bukkit.World();
    }
}
