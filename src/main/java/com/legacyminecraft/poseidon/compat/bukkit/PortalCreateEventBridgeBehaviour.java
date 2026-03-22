package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.Collection;

/**
 * Canonical Bukkit-compat bridge for portal creation event dispatch.
 */
public final class PortalCreateEventBridgeBehaviour {
    private static final PortalCreateEventBridgeBehaviour INSTANCE = new PortalCreateEventBridgeBehaviour();

    private PortalCreateEventBridgeBehaviour() {
    }

    public static PortalCreateEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelPortalCreation(Collection<org.bukkit.block.Block> changedBlocks, org.bukkit.World world) {
        PortalCreateEvent event = new PortalCreateEvent(changedBlocks, world);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
