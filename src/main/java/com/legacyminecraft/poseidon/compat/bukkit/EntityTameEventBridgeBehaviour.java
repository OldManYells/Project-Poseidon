package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.event.CraftEventFactory;

/**
 * Canonical Bukkit bridge behaviour for tame-event hook invocation.
 */
public final class EntityTameEventBridgeBehaviour {
    private static final EntityTameEventBridgeBehaviour INSTANCE = new EntityTameEventBridgeBehaviour();

    private EntityTameEventBridgeBehaviour() {
    }

    public static EntityTameEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isTamingAllowed(EntityLiving tameable, EntityHuman tamer) {
        return !CraftEventFactory.callEntityTameEvent(tameable, tamer).isCancelled();
    }
}
