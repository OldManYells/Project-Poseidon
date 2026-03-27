package com.legacyminecraft.compat.bukkit;


/**
 * Canonical compat bridge for checking legacy CraftBukkit shutdown state.
 */
public final class ServerShutdownStateBridgeBehaviour {
    private static final ServerShutdownStateBridgeBehaviour INSTANCE = new ServerShutdownStateBridgeBehaviour();
    private static final ServerWrapperProjectionBridgeBehaviour SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            ServerWrapperProjectionBridgeBehaviour.getInstance();

    private ServerShutdownStateBridgeBehaviour() {
    }

    public static ServerShutdownStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isShuttingDown(Object server) {
        CraftServer craftServer = SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(server);
        if (craftServer == null) {
            return false;
        }
        return invokeBoolean(craftServer, "isShuttingdown")
                || invokeBoolean(craftServer, "isShuttingDown");
    }

    private boolean invokeBoolean(Object target, String methodName) {
        try {
            java.lang.reflect.Method method = target.getClass().getMethod(methodName);
            Object value = method.invoke(target);
            return Boolean.TRUE.equals(value);
        } catch (Exception ignored) {
            return false;
        }
    }
}
