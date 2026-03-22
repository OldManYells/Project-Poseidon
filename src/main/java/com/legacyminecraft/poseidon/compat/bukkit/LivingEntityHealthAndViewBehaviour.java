package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import org.bukkit.Location;

/**
 * Canonical behaviour for CraftLivingEntity health validation/mutation and eye-view helpers.
 */
public final class LivingEntityHealthAndViewBehaviour {
    private static final int MIN_HEALTH = 0;
    private static final int MAX_HEALTH = 200;
    private static final double DEFAULT_EYE_HEIGHT = 1.0D;
    private static final LivingEntityHealthAndViewBehaviour INSTANCE = new LivingEntityHealthAndViewBehaviour();

    private LivingEntityHealthAndViewBehaviour() {
    }

    public static LivingEntityHealthAndViewBehaviour getInstance() {
        return INSTANCE;
    }

    public int getHealth(EntityLiving livingEntity) {
        return livingEntity.health;
    }

    public void setHealth(EntityLiving livingEntity, int health) {
        if (health < MIN_HEALTH || health > MAX_HEALTH) {
            throw new IllegalArgumentException("Health must be between 0 and 200");
        }

        if (livingEntity instanceof EntityPlayer && health == 0) {
            ((EntityPlayer) livingEntity).die((Entity) null);
        }

        livingEntity.health = health;
    }

    public double getDefaultEyeHeight() {
        return DEFAULT_EYE_HEIGHT;
    }

    public Location computeEyeLocation(Location baseLocation, double eyeHeight) {
        baseLocation.setY(baseLocation.getY() + eyeHeight);
        return baseLocation;
    }
}
