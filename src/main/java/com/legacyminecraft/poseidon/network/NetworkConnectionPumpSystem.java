package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.NetServerHandler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical per-tick pump for pending login handlers and active server handlers.
 */
public final class NetworkConnectionPumpSystem {
    private static final NetworkConnectionPumpSystem INSTANCE = new NetworkConnectionPumpSystem();

    private NetworkConnectionPumpSystem() {
    }

    public static NetworkConnectionPumpSystem getInstance() {
        return INSTANCE;
    }

    public void pumpConnections(List pendingLoginHandlers, List activeServerHandlers, Logger logger) {
        pumpPendingLogins(pendingLoginHandlers, logger);
        pumpActiveHandlers(activeServerHandlers, logger);
    }

    private void pumpPendingLogins(List pendingLoginHandlers, Logger logger) {
        for (int i = 0; i < pendingLoginHandlers.size(); ++i) {
            NetLoginHandler netloginhandler = (NetLoginHandler) pendingLoginHandlers.get(i);

            try {
                netloginhandler.a();
            } catch (Exception exception) {
                if (netloginhandler == null) {
                    logger.log(Level.WARNING, "Looks like someone tried to crash the server, stopped their attempt.");
                    pendingLoginHandlers.remove(i);
                    return;
                } else {
                    netloginhandler.disconnect("Internal server error");
                    logger.log(Level.WARNING, "Failed to handle packet: " + exception, exception);
                }
            }

            if (netloginhandler.c) {
                pendingLoginHandlers.remove(i--);
            }

            netloginhandler.networkManager.a();
        }
    }

    private void pumpActiveHandlers(List activeServerHandlers, Logger logger) {
        for (int i = 0; i < activeServerHandlers.size(); ++i) {
            NetServerHandler netserverhandler = (NetServerHandler) activeServerHandlers.get(i);

            try {
                netserverhandler.a();
            } catch (Exception exception1) {
                logger.log(Level.WARNING, "Failed to handle packet: " + exception1, exception1);
                netserverhandler.disconnect("Internal server error");
            }

            if (netserverhandler.disconnected) {
                activeServerHandlers.remove(i--);
            }

            netserverhandler.networkManager.a();
        }
    }
}
