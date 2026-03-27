package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.LivingEntityDamageStateBehaviour;
import com.legacyminecraft.compat.bukkit.LivingEntityHealthAndViewBehaviour;
import com.legacyminecraft.compat.bukkit.LivingEntityTargetingBehaviour;
import com.legacyminecraft.compat.bukkit.LivingEntityProjectileLaunchBehaviour;
import com.legacyminecraft.compat.bukkit.LivingEntityVehicleBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.LivingEntityContextBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.EntityWrapperDescriptionBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityHandleMutationBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.*;

import java.util.HashSet;
import java.util.List;

public class CraftLivingEntity extends CraftEntity implements LivingEntity {
    private static final LivingEntityDamageStateBehaviour LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR =
            LivingEntityDamageStateBehaviour.getInstance();
    private static final LivingEntityHealthAndViewBehaviour LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR =
            LivingEntityHealthAndViewBehaviour.getInstance();
    private static final LivingEntityProjectileLaunchBehaviour LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR =
            LivingEntityProjectileLaunchBehaviour.getInstance();
    private static final LivingEntityTargetingBehaviour LIVING_ENTITY_TARGETING_BEHAVIOUR =
            LivingEntityTargetingBehaviour.getInstance();
    private static final LivingEntityVehicleBridgeBehaviour LIVING_ENTITY_VEHICLE_BRIDGE_BEHAVIOUR =
            LivingEntityVehicleBridgeBehaviour.getInstance();
    private static final LivingEntityContextBridgeBehaviour LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR =
            LivingEntityContextBridgeBehaviour.getInstance();
    private static final EntityWrapperDescriptionBehaviour ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR =
            EntityWrapperDescriptionBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final EntityHandleMutationBehaviour ENTITY_HANDLE_MUTATION_BEHAVIOUR =
            EntityHandleMutationBehaviour.getInstance();

    public CraftLivingEntity(final CraftServer server, final EntityLiving entity) {
        super(server, entity);
    }

    public int getHealth() {
        return LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR.getHealth(getHandle());
    }

    public void setHealth(int health) {
        LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR.setHealth(getHandle(), health);
    }

    @Override
    public EntityLiving getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityLiving.class);
    }

    public void setHandle(final EntityLiving entity) {
        ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks() {
            @Override
            public void setSuperHandle(Object updatedHandle) {
                CraftLivingEntity.super.setHandle((Entity) updatedHandle);
            }

            @Override
            public void assignHandleField(Object updatedHandle) {
                CraftLivingEntity.this.entity = (EntityLiving) updatedHandle;
            }

            @Override
            public void afterHandleAssignment(Object updatedHandle) {
            }
        });
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR.craftLivingEntityToString(getEntityId());
    }

    public Egg throwEgg() {
        net.minecraft.server.World world = LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR.resolveWorldHandle(getWorld());
        return LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR.throwEgg(world, getHandle());
    }

    public Snowball throwSnowball() {
        net.minecraft.server.World world = LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR.resolveWorldHandle(getWorld());
        return LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR.throwSnowball(world, getHandle());
    }

    public double getEyeHeight() {
        return LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR.getDefaultEyeHeight();
    }

    public double getEyeHeight(boolean ignoreSneaking) {
        return getEyeHeight();
    }

    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return LIVING_ENTITY_TARGETING_BEHAVIOUR.collectLineOfSight(this, transparent, maxDistance, 0);
    }

    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        return LIVING_ENTITY_TARGETING_BEHAVIOUR.resolveTargetBlock(this, transparent, maxDistance);
    }

    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return LIVING_ENTITY_TARGETING_BEHAVIOUR.collectLastTargetBlocks(this, transparent, maxDistance, 2);
    }

    public Arrow shootArrow() {
        net.minecraft.server.World world = LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR.resolveWorldHandle(getWorld());
        return LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR.shootArrow(world, getHandle());
    }

    public boolean isInsideVehicle() {
        return LIVING_ENTITY_VEHICLE_BRIDGE_BEHAVIOUR.isInsideVehicle(getHandle());
    }

    public boolean leaveVehicle() {
        return LIVING_ENTITY_VEHICLE_BRIDGE_BEHAVIOUR.leaveVehicle(getHandle());
    }

    public Vehicle getVehicle() {
        return LIVING_ENTITY_VEHICLE_BRIDGE_BEHAVIOUR.resolveVehicle(getHandle());
    }

    public int getRemainingAir() {
        return LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.getRemainingAir(getHandle());
    }

    public void setRemainingAir(int ticks) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.setRemainingAir(getHandle(), ticks);
    }

    public int getMaximumAir() {
        return LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.getMaximumAir(getHandle());
    }

    public void setMaximumAir(int ticks) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.setMaximumAir(getHandle(), ticks);
    }

    public void damage(int amount) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.applyDamage(getHandle(), amount);
    }

    public void damage(int amount, org.bukkit.entity.Entity source) {
        net.minecraft.server.Entity sourceHandle = LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR.resolveDamageSourceHandle(source);
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.applyDamage(getHandle(), sourceHandle, amount);
    }

    public Location getEyeLocation() {
        return LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR.computeEyeLocation(getLocation(), getEyeHeight());
    }

    public int getMaximumNoDamageTicks() {
        return LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.getMaximumNoDamageTicks(getHandle());
    }

    public void setMaximumNoDamageTicks(int ticks) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.setMaximumNoDamageTicks(getHandle(), ticks);
    }

    public int getLastDamage() {
        return LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.getLastDamage(getHandle());
    }

    public void setLastDamage(int damage) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.setLastDamage(getHandle(), damage);
    }

    public int getNoDamageTicks() {
        return LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.getNoDamageTicks(getHandle());
    }

    public void setNoDamageTicks(int ticks) {
        LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR.setNoDamageTicks(getHandle(), ticks);
    }
}
