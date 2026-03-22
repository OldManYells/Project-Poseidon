package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntitySnowball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;

public class CraftSnowball extends AbstractProjectile implements Snowball {
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();

    public CraftSnowball(CraftServer server, EntitySnowball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftSnowball";
    }

    public LivingEntity getShooter() {
        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toBukkitShooter(((EntitySnowball) getHandle()).shooter);
    }

    public void setShooter(LivingEntity shooter) {
        EntityLiving shooterEntity = PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toNmsShooter(shooter);
        if (shooterEntity != null) {
            ((EntitySnowball) getHandle()).shooter = shooterEntity;
        }
    }
}
