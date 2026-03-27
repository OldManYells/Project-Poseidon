package com.legacyminecraft.compat.bukkit;


import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.world.PortalCreateEvent;

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

    public boolean shouldCancelPortalCreation(Collection<Block> changedBlocks, World world) {
        PortalCreateEvent event = new PortalCreateEvent(changedBlocks, world);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
