package com.legacyminecraft.poseidon.network;


/**
 * Canonical helper for teleport destination resolution and orientation sanitization.
 */
public final class PlayerTeleportCoordinator {
    private static final PlayerTeleportCoordinator INSTANCE = new PlayerTeleportCoordinator();

    private PlayerTeleportCoordinator() {
    }

    public static PlayerTeleportCoordinator getInstance() {
        return INSTANCE;
    }

    public Object resolveTeleportDestination(Object server, Object player, double x, double y, double z, float yaw, float pitch) {
        Object from = invoke(player, "getLocation");
        Object world = invoke(player, "getWorld");
        Object to = newLocation(world, x, y, z, yaw, pitch);
        Object event = newPlayerTeleportEvent(player, from, to);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);

        Object resolvedFrom = invoke(event, "getFrom");
        boolean cancelled = Boolean.TRUE.equals(invoke(event, "isCancelled"));
        return cancelled ? resolvedFrom : invoke(event, "getTo");
    }

    public TeleportPlan createTeleportPlan(Object destination) {
        double x = ((Number) invoke(destination, "getX")).doubleValue();
        double y = ((Number) invoke(destination, "getY")).doubleValue();
        double z = ((Number) invoke(destination, "getZ")).doubleValue();
        float yaw = sanitizeRotationComponent(((Number) invoke(destination, "getYaw")).floatValue());
        float pitch = sanitizeRotationComponent(((Number) invoke(destination, "getPitch")).floatValue());
        return new TeleportPlan(x, y, z, yaw, pitch);
    }

    public float sanitizeRotationComponent(float value) {
        return Float.isNaN(value) ? 0.0F : value;
    }

    public static final class TeleportPlan {
        private final double x;
        private final double y;
        private final double z;
        private final float yaw;
        private final float pitch;

        private TeleportPlan(double x, double y, double z, float yaw, float pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            java.lang.reflect.Method[] methods = target.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }

    private Object newLocation(Object world, double x, double y, double z, float yaw, float pitch) {
        return NetworkCompatGatewayRegistry.gateway().createLocation(world, x, y, z, yaw, pitch);
    }

    private Object newPlayerTeleportEvent(Object player, Object from, Object to) {
        return NetworkCompatGatewayRegistry.gateway().createPlayerTeleportEvent(player, from, to);
    }
}
