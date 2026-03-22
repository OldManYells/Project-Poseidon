package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;

/**
 * Canonical execution flow for processed respawn packet outcomes.
 */
public final class RespawnResultExecutionSystem {
    private static final RespawnResultExecutionSystem INSTANCE = new RespawnResultExecutionSystem();

    private RespawnResultExecutionSystem() {
    }

    public static RespawnResultExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void executeResult(
            RespawnPacketHandler.RespawnResult respawnResult,
            RespawnActions respawnActions
    ) {
        if (respawnResult.isRespawned()) {
            respawnActions.applyRespawnedPlayer(respawnResult.getPlayer());
        }
    }

    public interface RespawnActions {
        void applyRespawnedPlayer(EntityPlayer player);
    }
}
