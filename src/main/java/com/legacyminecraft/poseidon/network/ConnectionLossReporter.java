package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet3Chat;

import java.util.logging.Logger;

/**
 * Canonical service for handling lost-connection reporting and quit broadcast.
 */
public final class ConnectionLossReporter {
    private static final ConnectionLossReporter INSTANCE = new ConnectionLossReporter();

    private ConnectionLossReporter() {
    }

    public static ConnectionLossReporter getInstance() {
        return INSTANCE;
    }

    public boolean reportAndDisconnect(MinecraftServer minecraftServer, EntityPlayer player, String reason, Logger logger) {
        if (shouldLogDisconnectReason(
                (boolean) PoseidonConfig.getInstance().getConfigOption("settings.remove-join-leave-debug", true),
                reason
        )) {
            logger.info(player.name + " lost connection: " + reason);
        }

        logger.info(player.name + " has left the game.");
        String quitMessage = minecraftServer.serverConfigurationManager.disconnect(player);
        if (quitMessage != null) {
            minecraftServer.serverConfigurationManager.sendAll(new Packet3Chat(quitMessage));
        }
        return true;
    }

    public boolean shouldLogDisconnectReason(boolean removeJoinLeaveDebug, String reason) {
        return !removeJoinLeaveDebug || !"disconnect.quitting".equals(reason);
    }
}
