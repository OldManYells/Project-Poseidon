package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for CraftEntity nearby-entity and passenger bridge operations.
 */
public final class EntityInteractionBridgeBehaviour {
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE_BEHAVIOUR = EntityHandleBridgeBehaviour.getInstance();
    private static final EntityInteractionBridgeBehaviour INSTANCE = new EntityInteractionBridgeBehaviour();

    private EntityInteractionBridgeBehaviour() {
    }

    public static EntityInteractionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public List<com.legacyminecraft.compat.bukkit.entity.Entity> collectNearbyEntities(Entity sourceEntity, double x, double y, double z) {
        @SuppressWarnings("unchecked")
        List<Entity> nearbyNotchEntities = sourceEntity.world.b(sourceEntity, sourceEntity.boundingBox.b(x, y, z));
        List<com.legacyminecraft.compat.bukkit.entity.Entity> nearbyBukkitEntities =
                new ArrayList<com.legacyminecraft.compat.bukkit.entity.Entity>(nearbyNotchEntities.size());

        for (Entity nearbyNotchEntity : nearbyNotchEntities) {
            nearbyBukkitEntities.add(nearbyNotchEntity.getBukkitEntity());
        }

        return nearbyBukkitEntities;
    }

    public com.legacyminecraft.compat.bukkit.entity.Entity getPassenger(Entity sourceEntity) {
        if (sourceEntity.passenger == null) {
            return null;
        }
        return sourceEntity.passenger.getBukkitEntity();
    }

    public boolean setPassenger(Entity sourceEntity, com.legacyminecraft.compat.bukkit.entity.Entity bukkitPassenger) {
        Entity passengerHandle = ENTITY_HANDLE_BRIDGE_BEHAVIOUR.resolveHandle(bukkitPassenger);
        if (passengerHandle == null) {
            return false;
        }
        passengerHandle.setPassengerOf(sourceEntity);
        return true;
    }

    public boolean isPassengerSlotEmpty(Entity sourceEntity) {
        return sourceEntity.passenger == null;
    }

    public boolean ejectPassenger(Entity sourceEntity) {
        if (sourceEntity.passenger == null) {
            return false;
        }
        sourceEntity.passenger.setPassengerOf(null);
        return true;
    }
}
