package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;

import java.util.logging.Logger;

/**
 * Canonical service for handling lost-connection reporting and quit broadcast.
 */
public final class ConnectionLossReporter {
    private static final ConnectionLossReporter INSTANCE = new ConnectionLossReporter();
    private final NetworkDisconnectKeyPolicy networkDisconnectKeyPolicy = NetworkDisconnectKeyPolicy.getInstance();
    private final ConnectionLossDebugConfigPolicy connectionLossDebugConfigPolicy =
            ConnectionLossDebugConfigPolicy.getInstance();

    private ConnectionLossReporter() {
    }

    public static ConnectionLossReporter getInstance() {
        return INSTANCE;
    }

    public boolean reportAndDisconnect(Object minecraftServer, Object player, String reason, Logger logger) {
        String playerName = String.valueOf(getField(player, "name"));
        if (shouldLogDisconnectReason(
                (boolean) PoseidonConfig.getInstance().getConfigOption(
                        connectionLossDebugConfigPolicy.removeJoinLeaveDebugKey(),
                        connectionLossDebugConfigPolicy.removeJoinLeaveDebugDefault()
                ),
                reason
        )) {
            logger.info(playerName + " lost connection: " + reason);
        }

        logger.info(playerName + " has left the game.");
        Object scm = getField(minecraftServer, "serverConfigurationManager");
        String quitMessage = cast(invoke(scm, "disconnect", player));
        if (quitMessage != null) {
            Object packet = NetworkCompatGatewayRegistry.gateway().createChatPacket(quitMessage);
            invoke(scm, "sendAll", packet);
        }
        return true;
    }

    public boolean shouldLogDisconnectReason(boolean removeJoinLeaveDebug, String reason) {
        return !removeJoinLeaveDebug || !networkDisconnectKeyPolicy.quitting().equals(reason);
    }

    private Object getField(Object target, String name) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
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

    @SuppressWarnings("unchecked")
    private <T> T cast(Object value) {
        return (T) value;
    }
}
