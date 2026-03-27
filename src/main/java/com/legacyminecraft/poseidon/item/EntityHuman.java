package com.legacyminecraft.poseidon.item;

/**
 * Item-local human entity scaffold.
 */
public class EntityHuman extends com.legacyminecraft.compat.bukkit.EntityHuman {
    public float lastPitch;
    public float lastYaw;
    public double lastX;
    public double lastY;
    public double lastZ;
    public float height = 1.62F;
    public EntityFish hookedFish;

    public void w() {
    }
}
