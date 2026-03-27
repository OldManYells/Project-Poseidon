package com.legacyminecraft.compat.bukkit;

/**
 * Canonical bridge for converting projectile shooter state between NMS and Bukkit wrappers.
 */
public final class ProjectileShooterBridgeBehaviour {
    private static final ProjectileShooterBridgeBehaviour INSTANCE = new ProjectileShooterBridgeBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR =
            EntityHandleBridgeBehaviour.getInstance();

    private ProjectileShooterBridgeBehaviour() {
    }

    public static ProjectileShooterBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.entity.LivingEntity toBukkitShooter(net.minecraft.server.EntityLiving shooter) {
        if (shooter == null) {
            return null;
        }
        return (org.bukkit.entity.LivingEntity) shooter.getBukkitEntity();
    }

    public net.minecraft.server.EntityLiving toNmsShooter(org.bukkit.entity.LivingEntity shooter) {
        return ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveLivingHandle(shooter);
    }
}
