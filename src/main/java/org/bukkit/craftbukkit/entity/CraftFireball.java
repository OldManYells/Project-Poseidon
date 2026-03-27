package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.FireballEntityPropertyBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileEntityShooterFieldBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileShooterAssignmentBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityFireball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class CraftFireball extends AbstractProjectile implements Fireball {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final FireballEntityPropertyBehaviour FIREBALL_ENTITY_PROPERTY_BEHAVIOUR =
            FireballEntityPropertyBehaviour.getInstance();
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();
    private static final ProjectileEntityShooterFieldBehaviour PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR =
            ProjectileEntityShooterFieldBehaviour.getInstance();
    private static final ProjectileShooterAssignmentBehaviour PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR =
            ProjectileShooterAssignmentBehaviour.getInstance();

    public CraftFireball(CraftServer server, EntityFireball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftFireball");
    }

    public float getYield() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.getYield(getFireballHandle());
    }

    public boolean isIncendiary() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.isIncendiary(getFireballHandle());
    }

    public void setIsIncendiary(boolean isIncendiary) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setIncendiary(getFireballHandle(), isIncendiary);
    }

    public void setYield(float yield) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setYield(getFireballHandle(), yield);
    }

    public LivingEntity getShooter() {
        return (LivingEntity) PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.toBukkitShooter(
                PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.getFireballShooter(getFireballHandle()),
                new ProjectileShooterAssignmentBehaviour.ShooterProjection() {
                    @Override
                    public Object toBukkitShooter(Object shooterEntity) {
                        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toBukkitShooter(
                                (net.minecraft.server.EntityLiving) shooterEntity
                        );
                    }
                }
        );
    }

    public void setShooter(LivingEntity shooter) {
        PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.assignShooter(
                shooter,
                new ProjectileShooterAssignmentBehaviour.ShooterConversion() {
                    @Override
                    public Object toNmsShooter(Object shooterObject) {
                        return PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR.toNmsShooter((LivingEntity) shooterObject);
                    }
                },
                new ProjectileShooterAssignmentBehaviour.ShooterAssignment() {
                    @Override
                    public void assign(Object shooterEntity) {
                        PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.setFireballShooter(
                                getFireballHandle(),
                                (net.minecraft.server.EntityLiving) shooterEntity
                        );
                    }
                }
        );
    }

    public Vector getDirection() {
        return FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.getDirection(getFireballHandle());
    }

    public void setDirection(Vector direction) {
        FIREBALL_ENTITY_PROPERTY_BEHAVIOUR.setDirection(getFireballHandle(), direction);
    }

    private EntityFireball getFireballHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityFireball.class);
    }
}
