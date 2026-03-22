package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Canonical behavior for CraftWorld chunk refresh notifications and in-use safety checks.
 */
public final class CraftWorldChunkLifecycleBehaviour {
    private static final CraftWorldChunkLifecycleBehaviour INSTANCE = new CraftWorldChunkLifecycleBehaviour();

    private CraftWorldChunkLifecycleBehaviour() {
    }

    public static CraftWorldChunkLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void notifyChunkRefresh(WorldServer worldServer, int chunkX, int chunkZ) {
        int blockX = chunkX << 4;
        int blockZ = chunkZ << 4;

        for (int x = blockX; x < (blockX + 16); x++) {
            worldServer.notify(x, 0, blockZ);
        }
        worldServer.notify(blockX, 127, blockZ + 15);
    }

    public boolean isChunkInUse(Player[] onlinePlayers, World chunkWorld, int chunkX, int chunkZ, int protectionRadiusBlocks) {
        int chunkBlockX = chunkX << 4;
        int chunkBlockZ = chunkZ << 4;

        for (Player player : onlinePlayers) {
            Location playerLocation = player.getLocation();
            if (playerLocation.getWorld() != chunkWorld) {
                continue;
            }

            if (Math.abs(playerLocation.getBlockX() - chunkBlockX) <= protectionRadiusBlocks
                    && Math.abs(playerLocation.getBlockZ() - chunkBlockZ) <= protectionRadiusBlocks) {
                return true;
            }
        }
        return false;
    }
}

