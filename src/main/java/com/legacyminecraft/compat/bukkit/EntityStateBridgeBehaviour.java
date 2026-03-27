package com.legacyminecraft.compat.bukkit;


import java.util.UUID;
import net.minecraft.server.Entity;

/**
 * Canonical behaviour for CraftEntity core state bridge operations.
 */
public final class EntityStateBridgeBehaviour {
    private static final EntityStateBridgeBehaviour INSTANCE = new EntityStateBridgeBehaviour();

    private EntityStateBridgeBehaviour() {
    }

    public static EntityStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public int getEntityId(Entity entity) {
        return entity.id;
    }

    public int getFireTicks(Entity entity) {
        return entity.fireTicks;
    }

    public int getMaxFireTicks(Entity entity) {
        return entity.maxFireTicks;
    }

    public void setFireTicks(Entity entity, int ticks) {
        entity.fireTicks = ticks;
    }

    public void remove(Entity entity) {
        entity.dead = true;
    }

    public boolean isDead(Entity entity) {
        return entity.dead;
    }

    public UUID getUniqueId(Entity entity) {
        return entity.uniqueId;
    }
}
