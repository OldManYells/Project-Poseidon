package com.legacyminecraft.poseidon.auth.login;


import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

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

    public void disconnect(Object networkManager, Logger logger, String identity, String reason) {
        try {
            logger.info(createDisconnectLogMessage(identity, reason));
            Object disconnectPacket = Bridge.newKickPacket(reason);
            Bridge.invoke(networkManager, "queue", disconnectPacket);
            Bridge.invoke(networkManager, "d");
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

    private static final class Bridge {
        private static Object newKickPacket(String reason) {
            return NetworkCompatGatewayRegistry.gateway().createKickPacket(reason);
        }

        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
