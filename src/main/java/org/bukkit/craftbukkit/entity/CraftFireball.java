package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.FireballEntityPropertyBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class CraftFireball extends AbstractProjectile implements Fireball {
    private static final FireballEntityPropertyBehaviour FIREBALL_ENTITY_PROPERTY_BEHAVIOUR =
            FireballEntityPropertyBehaviour.getInstance();
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();

    public CraftFireball(CraftServer server, EntityFireball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftFireball";
    }

    public float getYield() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.getYield((EntityFireball) getHandle());
    }

    public boolean isIncendiary() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.isIncendiary((EntityFireball) getHandle());
    }

    public void setIsIncendiary(boolean isIncendiary) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setIncendiary((EntityFireball) getHandle(), isIncendiary);
    }

    public void setYield(float yield) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setYield((EntityFireball) getHandle(), yield);
    }

    public LivingEntity getShooter() {
        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toBukkitShooter(((EntityFireball) getHandle()).shooter);
    }

    public void setShooter(LivingEntity shooter) {
        EntityLiving shooterEntity = PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toNmsShooter(shooter);
        if (shooterEntity != null) {
            ((EntityFireball) getHandle()).shooter = shooterEntity;
        }
    }

    public Vector getDirection() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.getDirection((EntityFireball) getHandle());
    }

    public void setDirection(Vector direction) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setDirection((EntityFireball) getHandle(), direction);
    }
}
