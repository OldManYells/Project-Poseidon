package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Canonical bridge for converting projectile shooter state between NMS and Bukkit wrappers.
 */
public final class ProjectileShooterBridgeBehaviour {
    private static final ProjectileShooterBridgeBehaviour INSTANCE = new ProjectileShooterBridgeBehaviour();

    private ProjectileShooterBridgeBehaviour() {
    }

    public static ProjectileShooterBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public LivingEntity toBukkitShooter(EntityLiving shooter) {
        if (shooter == null) {
            return null;
        }
        return (LivingEntity) shooter.getBukkitEntity();
    }

    public EntityLiving toNmsShooter(LivingEntity shooter) {
        if (!(shooter instanceof CraftLivingEntity)) {
            return null;
        }
        return ((CraftLivingEntity) shooter).getHandle();
    }
}
