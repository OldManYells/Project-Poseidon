package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.LivingEntity;

public class CraftEgg extends AbstractProjectile implements Egg {
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();

    public CraftEgg(CraftServer server, EntityEgg entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftEgg";
    }

    public LivingEntity getShooter() {
        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toBukkitShooter(((EntityEgg) getHandle()).thrower);
    }

    public void setShooter(LivingEntity shooter) {
        EntityLiving shooterEntity = PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toNmsShooter(shooter);
        if (shooterEntity != null) {
            ((EntityEgg) getHandle()).thrower = shooterEntity;
        }
    }
}
