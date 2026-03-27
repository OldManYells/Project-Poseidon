package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat localization shim used by migrated item behaviour.
 */
public final class StatisticCollector {
    private StatisticCollector() {
    }

    public static String a(String key) {
        try {
            Class<?> legacy = Class.forName("net.minecraft.server.StatisticCollector");
            Object value = legacy.getMethod("a", String.class).invoke(null, key);
            return value instanceof String ? (String) value : key;
        } catch (ReflectiveOperationException ignored) {
            return key;
        }
    }
}
