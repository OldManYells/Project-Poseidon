package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit-compat bridge for world lightning-strike event dispatch.
 */
public final class WorldLightningEventBridgeBehaviour {
    private static final WorldLightningEventBridgeBehaviour INSTANCE = new WorldLightningEventBridgeBehaviour();

    private WorldLightningEventBridgeBehaviour() {
    }

    public static WorldLightningEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelLightning(Object worldServer, Object lightningEntity) {
        try {
            Object bukkitWorld = invoke(worldServer, "getWorld");
            Object bukkitEntity = invoke(lightningEntity, "getBukkitEntity");
            Class<?> eventClass = Class.forName("org.bukkit.event.weather.LightningStrikeEvent");
            Object lightningEvent = eventClass
                    .getConstructor(Class.forName("org.bukkit.World"), Class.forName("org.bukkit.entity.LightningStrike"))
                    .newInstance(bukkitWorld, bukkitEntity);
            Object bukkitServer = invoke(worldServer, "getServer");
            Object pluginManager = invoke(bukkitServer, "getPluginManager");
            pluginManager.getClass().getMethod("callEvent", Class.forName("org.bukkit.event.Event")).invoke(pluginManager, lightningEvent);
            return ((Boolean) eventClass.getMethod("isCancelled").invoke(lightningEvent)).booleanValue();
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private static Object invoke(Object target, String methodName) throws ReflectiveOperationException {
        return target.getClass().getMethod(methodName).invoke(target);
    }
}
