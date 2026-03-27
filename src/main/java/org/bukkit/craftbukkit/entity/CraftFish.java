package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.FishHookOwnerBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileEntityShooterFieldBehaviour;
import com.legacyminecraft.compat.bukkit.ProjectileShooterAssignmentBehaviour;
import net.minecraft.server.EntityFish;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;

public class CraftFish extends AbstractProjectile implements Fish {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final FishHookOwnerBridgeBehaviour FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR =
            FishHookOwnerBridgeBehaviour.getInstance();
    private static final ProjectileEntityShooterFieldBehaviour PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR =
            ProjectileEntityShooterFieldBehaviour.getInstance();
    private static final ProjectileShooterAssignmentBehaviour PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR =
            ProjectileShooterAssignmentBehaviour.getInstance();

    public CraftFish(CraftServer server, EntityFish entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftFish");
    }

    public LivingEntity getShooter() {
        return (LivingEntity) PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.toBukkitShooter(
                PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.getFishOwner(getFishHandle()),
                new ProjectileShooterAssignmentBehaviour.ShooterProjection() {
                    @Override
                    public Object toBukkitShooter(Object shooterEntity) {
                        return FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR.toBukkitOwner(
                                (net.minecraft.server.EntityHuman) shooterEntity
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
                        return FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR.toNmsOwner((LivingEntity) shooterObject);
                    }
                },
                new ProjectileShooterAssignmentBehaviour.ShooterAssignment() {
                    @Override
                    public void assign(Object shooterEntity) {
                        PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR.setFishOwner(
                                getFishHandle(),
                                (net.minecraft.server.EntityHuman) shooterEntity
                        );
                    }
                }
        );
    }

    private EntityFish getFishHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityFish.class);
    }

}
