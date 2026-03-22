package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import org.bukkit.craftbukkit.entity.CraftEntity;

/**
 * Canonical Bukkit bridge behaviour for resolving NMS handles from Bukkit entities.
 */
public final class EntityHandleBridgeBehaviour {
    private static final EntityHandleBridgeBehaviour INSTANCE = new EntityHandleBridgeBehaviour();

    private EntityHandleBridgeBehaviour() {
    }

    public static EntityHandleBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Entity resolveHandle(org.bukkit.entity.Entity bukkitEntity) {
        if (bukkitEntity == null) {
            return null;
        }
        return ((CraftEntity) bukkitEntity).getHandle();
    }
}
