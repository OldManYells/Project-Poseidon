package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;

public class CraftArrow extends AbstractProjectile implements Arrow {
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();

    public CraftArrow(CraftServer server, EntityArrow entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftArrow";
    }

    public LivingEntity getShooter() {
        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toBukkitShooter(((EntityArrow) getHandle()).shooter);
    }

    public void setShooter(LivingEntity shooter) {
        EntityLiving shooterEntity = PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toNmsShooter(shooter);
        if (shooterEntity != null) {
            ((EntityArrow) getHandle()).shooter = shooterEntity;
        }
    }
}
