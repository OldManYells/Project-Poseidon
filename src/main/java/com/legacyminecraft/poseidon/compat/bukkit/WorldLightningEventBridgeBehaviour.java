package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.WorldServer;
import org.bukkit.event.weather.LightningStrikeEvent;

/**
 * Canonical Bukkit-compat bridge for world lightning-strike event dispatch.
 */
public final class WorldLightningEventBridgeBehaviour {
    private static final WorldLightningEventBridgeBehaviour INSTANCE = new WorldLightningEventBridgeBehaviour();

    private WorldLightningEventBridgeBehaviour() {
    }

    public static WorldLightningEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelLightning(WorldServer worldServer, Entity lightningEntity) {
        LightningStrikeEvent lightningEvent = new LightningStrikeEvent(
                worldServer.getWorld(),
                (org.bukkit.entity.LightningStrike) lightningEntity.getBukkitEntity()
        );
        worldServer.getServer().getPluginManager().callEvent(lightningEvent);
        return lightningEvent.isCancelled();
    }
}
