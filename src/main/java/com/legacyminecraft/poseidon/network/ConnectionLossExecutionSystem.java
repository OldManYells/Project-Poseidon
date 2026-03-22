package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * Canonical execution flow for connection-loss disconnect handling.
 */
public final class ConnectionLossExecutionSystem {
    private static final ConnectionLossExecutionSystem INSTANCE = new ConnectionLossExecutionSystem();

    private ConnectionLossExecutionSystem() {
    }

    public static ConnectionLossExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean executeConnectionLoss(boolean disconnected, ConnectionLossActions connectionLossActions) {
        if (disconnected) {
            return true;
        }

        return connectionLossActions.reportAndDisconnect();
    }

    public boolean executeConnectionLoss(
            boolean disconnected,
            ConnectionLossReporter connectionLossReporter,
            MinecraftServer minecraftServer,
            EntityPlayer player,
            String reason,
            java.util.logging.Logger logger
    ) {
        if (disconnected) {
            return true;
        }
        return connectionLossReporter.reportAndDisconnect(minecraftServer, player, reason, logger);
    }

    public interface ConnectionLossActions {
        boolean reportAndDisconnect();
    }
}
