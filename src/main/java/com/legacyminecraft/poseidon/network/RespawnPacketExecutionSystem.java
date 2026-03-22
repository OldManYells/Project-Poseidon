package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for respawn packet handling and execution.
 */
public final class RespawnPacketExecutionSystem {
    private static final RespawnPacketExecutionSystem INSTANCE = new RespawnPacketExecutionSystem();

    private RespawnPacketExecutionSystem() {
    }

    public static RespawnPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(
            RespawnResultResolver respawnResultResolver,
            RespawnResultExecutionSystem respawnResultExecutionSystem,
            RespawnResultExecutionSystem.RespawnActions respawnActions
    ) {
        RespawnPacketHandler.RespawnResult respawnResult = respawnResultResolver.resolve();
        respawnResultExecutionSystem.executeResult(respawnResult, respawnActions);
    }

    public interface RespawnResultResolver {
        RespawnPacketHandler.RespawnResult resolve();
    }
}
