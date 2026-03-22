package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

/**
 * Canonical Bukkit bridge behaviour for world entity spawn-cancellation checks.
 */
public final class WorldEntitySpawnEventBridgeBehaviour {
    private static final WorldEntitySpawnEventBridgeBehaviour INSTANCE = new WorldEntitySpawnEventBridgeBehaviour();

    private WorldEntitySpawnEventBridgeBehaviour() {
    }

    public static WorldEntitySpawnEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelSpawn(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
        if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
            CreatureSpawnEvent event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving) entity, spawnReason);
            return event.isCancelled();
        }

        if (entity instanceof EntityItem) {
            ItemSpawnEvent event = CraftEventFactory.callItemSpawnEvent((EntityItem) entity);
            return event.isCancelled();
        }

        return false;
    }
}
