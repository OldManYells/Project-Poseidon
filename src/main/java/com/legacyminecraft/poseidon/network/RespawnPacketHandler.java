package com.legacyminecraft.poseidon.network;


/**
 * Canonical handler for Packet9Respawn server-side behavior.
 */
public final class RespawnPacketHandler {
    private static final RespawnPacketHandler INSTANCE = new RespawnPacketHandler();

    private RespawnPacketHandler() {
    }

    public static RespawnPacketHandler getInstance() {
        return INSTANCE;
    }

    public RespawnResult handleRespawnPacket(Object minecraftServer, Object currentPlayer) {
        int health = ((Number) getField(currentPlayer, "health")).intValue();
        if (!shouldRespawn(health)) {
            return RespawnResult.noRespawn(currentPlayer);
        }

        Object serverConfigurationManager = getField(minecraftServer, "serverConfigurationManager");
        Object respawnedPlayer = invoke(serverConfigurationManager, "moveToWorld", currentPlayer, 0);
        return RespawnResult.respawned(respawnedPlayer);
    }

    public boolean shouldRespawn(int health) {
        return health <= 0;
    }

    public static final class RespawnResult {
        private final boolean respawned;
        private final Object player;

        private RespawnResult(boolean respawned, Object player) {
            this.respawned = respawned;
            this.player = player;
        }

        public static RespawnResult noRespawn(Object player) {
            return new RespawnResult(false, player);
        }

        public static RespawnResult respawned(Object player) {
            return new RespawnResult(true, player);
        }

        public boolean isRespawned() {
            return respawned;
        }

        public Object getPlayer() {
            return player;
        }
    }

    private Object getField(Object target, String fieldName) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
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
}
