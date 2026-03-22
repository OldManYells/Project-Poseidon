package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.World;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Canonical Bukkit bridge behaviour for block-physics events raised by world updates.
 */
public final class WorldBlockPhysicsEventBridgeBehaviour {
    private static final WorldBlockPhysicsEventBridgeBehaviour INSTANCE = new WorldBlockPhysicsEventBridgeBehaviour();

    private WorldBlockPhysicsEventBridgeBehaviour() {
    }

    public static WorldBlockPhysicsEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelPhysics(World world, int x, int y, int z, int sourceTypeId) {
        if (!(world instanceof WorldServer)) {
            return false;
        }

        CraftWorld craftWorld = ((WorldServer) world).getWorld();
        if (craftWorld == null) {
            return false;
        }

        BlockPhysicsEvent event = new BlockPhysicsEvent(craftWorld.getBlockAt(x, y, z), sourceTypeId);
        world.getServer().getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
