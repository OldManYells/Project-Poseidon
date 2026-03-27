package com.legacyminecraft.poseidon.network;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical per-tick pump for pending login handlers and active server handlers.
 */
public final class NetworkConnectionPumpSystem {
    private static final NetworkConnectionPumpSystem INSTANCE = new NetworkConnectionPumpSystem();
    private final NetworkInternalServerErrorPolicy networkInternalServerErrorPolicy =
            NetworkInternalServerErrorPolicy.getInstance();

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
            Object netloginhandler = pendingLoginHandlers.get(i);

            try {
                Bridge.invoke(netloginhandler, "a");
            } catch (Exception exception) {
                if (netloginhandler == null) {
                    logger.log(Level.WARNING, "Looks like someone tried to crash the server, stopped their attempt.");
                    pendingLoginHandlers.remove(i);
                    return;
                } else {
                    Bridge.invoke(netloginhandler, "disconnect", networkInternalServerErrorPolicy.internalServerErrorMessage());
                    logger.log(Level.WARNING, "Failed to handle packet: " + exception, exception);
                }
            }

            if (Boolean.TRUE.equals(Bridge.readField(netloginhandler, "c"))) {
                pendingLoginHandlers.remove(i--);
            }

            Bridge.invoke(Bridge.readField(netloginhandler, "networkManager"), "a");
        }
    }

    private void pumpActiveHandlers(List activeServerHandlers, Logger logger) {
        for (int i = 0; i < activeServerHandlers.size(); ++i) {
            Object netserverhandler = activeServerHandlers.get(i);

            try {
                Bridge.invoke(netserverhandler, "a");
            } catch (Exception exception1) {
                logger.log(Level.WARNING, "Failed to handle packet: " + exception1, exception1);
                Bridge.invoke(netserverhandler, "disconnect", networkInternalServerErrorPolicy.internalServerErrorMessage());
            }

            if (Boolean.TRUE.equals(Bridge.readField(netserverhandler, "disconnected"))) {
                activeServerHandlers.remove(i--);
            }

            Bridge.invoke(Bridge.readField(netserverhandler, "networkManager"), "a");
        }
    }

    private static final class Bridge {
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

        private static Object readField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
