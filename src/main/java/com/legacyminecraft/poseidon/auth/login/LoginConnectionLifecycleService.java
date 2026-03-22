package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet255KickDisconnect;

import java.util.logging.Logger;

/**
 * Canonical lifecycle helpers for login-network disconnect and loss reporting.
 */
public final class LoginConnectionLifecycleService {
    private static final LoginConnectionLifecycleService INSTANCE = new LoginConnectionLifecycleService();
    private static final String PROTOCOL_ERROR_MESSAGE = "Protocol error";

    private LoginConnectionLifecycleService() {
    }

    public static LoginConnectionLifecycleService getInstance() {
        return INSTANCE;
    }

    public void disconnect(NetworkManager networkManager, Logger logger, String identity, String reason) {
        try {
            logger.info(createDisconnectLogMessage(identity, reason));
            networkManager.queue(new Packet255KickDisconnect(reason));
            networkManager.d();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void reportConnectionLost(Logger logger, String identity) {
        logger.info(createConnectionLostLogMessage(identity));
    }

    public String createDisconnectLogMessage(String identity, String reason) {
        return "Disconnecting " + identity + ": " + reason;
    }

    public String createConnectionLostLogMessage(String identity) {
        return identity + " lost connection";
    }

    public String getProtocolErrorMessage() {
        return PROTOCOL_ERROR_MESSAGE;
    }
}
