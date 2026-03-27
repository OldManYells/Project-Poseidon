package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileEntityShooterFieldBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileShooterAssignmentBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileShooterBridgeBehaviour;
import net.minecraft.server.EntityArrow;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;

public class CraftArrow extends AbstractProjectile implements Arrow {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final ProjectileShooterBridgeBehaviour PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR =
            ProjectileShooterBridgeBehaviour.getInstance();
    private static final ProjectileEntityShooterFieldBehaviour PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR =
            ProjectileEntityShooterFieldBehaviour.getInstance();
    private static final ProjectileShooterAssignmentBehaviour PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR =
            ProjectileShooterAssignmentBehaviour.getInstance();

    public CraftArrow(CraftServer server, EntityArrow entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftArrow");
    }

    public LivingEntity getShooter() {
        return (LivingEntity) PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.toBukkitShooter(
                PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.getArrowShooter(getArrowHandle()),
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
                        PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.setArrowShooter(
                                getArrowHandle(),
                                (net.minecraft.server.EntityLiving) shooterEntity
                        );
                    }
                }
        );
    }

    private EntityArrow getArrowHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityArrow.class);
    }
}
