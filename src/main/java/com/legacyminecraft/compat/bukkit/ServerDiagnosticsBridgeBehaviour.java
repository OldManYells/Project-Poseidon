package com.legacyminecraft.compat.bukkit;


import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Canonical compat bridge for reading legacy CraftBukkit diagnostic metadata.
 */
public final class ServerDiagnosticsBridgeBehaviour {
    private static final ServerDiagnosticsBridgeBehaviour INSTANCE = new ServerDiagnosticsBridgeBehaviour();

    private ServerDiagnosticsBridgeBehaviour() {
    }

    public static ServerDiagnosticsBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public LinkedList<Double> readTpsRecords(Server server) {
        Object minecraftServer = invoke(server, "getServer");
        Object records = invoke(minecraftServer, "getTpsRecords");
        if (records instanceof LinkedList) {
            return (LinkedList<Double>) records;
        }
        return new LinkedList<Double>();
    }

    public String readPoseidonVersion(Server server, String fallback) {
        return invokeString(server, "getPoseidonVersion", fallback);
    }

    public String readPoseidonReleaseType(Server server, String fallback) {
        return invokeString(server, "getPoseidonReleaseType", fallback);
    }

    private static String invokeString(Object target, String methodName, String fallback) {
        Object value = invoke(target, methodName);
        return value instanceof String ? (String) value : fallback;
    }

    private static Object invoke(Object target, String methodName) {
        if (target == null) {
            return null;
        }
        try {
            Method method = target.getClass().getMethod(methodName);
            return method.invoke(target);
        } catch (Exception ignored) {
            return null;
        }
    }
}
