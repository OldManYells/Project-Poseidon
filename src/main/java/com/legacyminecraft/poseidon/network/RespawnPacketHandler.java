package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * Canonical handler for Packet9Respawn server-side behavior.
 */
public final class RespawnPacketHandler {
    private static final RespawnPacketHandler INSTANCE = new RespawnPacketHandler();

    private RespawnPacketHandler() {
    }

    public static RespawnPacketHandler getInstance() {
        return INSTANCE;
    }

    public RespawnResult handleRespawnPacket(MinecraftServer minecraftServer, EntityPlayer currentPlayer) {
        if (!shouldRespawn(currentPlayer.health)) {
            return RespawnResult.noRespawn(currentPlayer);
        }

        EntityPlayer respawnedPlayer = minecraftServer.serverConfigurationManager.moveToWorld(currentPlayer, 0);
        return RespawnResult.respawned(respawnedPlayer);
    }

    public boolean shouldRespawn(int health) {
        return health <= 0;
    }

    public static final class RespawnResult {
        private final boolean respawned;
        private final EntityPlayer player;

        private RespawnResult(boolean respawned, EntityPlayer player) {
            this.respawned = respawned;
            this.player = player;
        }

        public static RespawnResult noRespawn(EntityPlayer player) {
            return new RespawnResult(false, player);
        }

        public static RespawnResult respawned(EntityPlayer player) {
            return new RespawnResult(true, player);
        }

        public boolean isRespawned() {
            return respawned;
        }

        public EntityPlayer getPlayer() {
            return player;
        }
    }
}
