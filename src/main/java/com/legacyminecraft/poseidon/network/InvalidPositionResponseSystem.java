package com.legacyminecraft.poseidon.network;


import java.util.logging.Logger;

/**
 * Canonical response handler for invalid numeric movement packets.
 */
public final class InvalidPositionResponseSystem {
    private static final Logger LOGGER = Logger.getLogger(InvalidPositionResponseSystem.class.getName());
    private static final InvalidPositionResponseSystem INSTANCE = new InvalidPositionResponseSystem();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();
    private final InvalidPositionLogBehaviour invalidPositionLogBehaviour = InvalidPositionLogBehaviour.getInstance();

    private InvalidPositionResponseSystem() {
    }

    public static InvalidPositionResponseSystem getInstance() {
        return INSTANCE;
    }

    public boolean handleInvalidPositionIfNeeded(Object packet10flying, Object player, boolean disconnected) {
        if (!movementPacketPolicy.hasInvalidNumericPosition(packet10flying, player, disconnected)) {
            return false;
        }

        Object world = invoke(player, "getWorld");
        Object spawnLocation = invoke(world, "getSpawnLocation");
        invoke(player, "teleport", spawnLocation);
        invalidPositionLogBehaviour.logInvalidPosition(LOGGER, createInvalidPositionLogMessage(String.valueOf(invoke(player, "getName"))));
        invoke(player, "kickPlayer", getInvalidPositionKickMessage());
        return true;
    }

    public String createInvalidPositionLogMessage(String playerName) {
        return playerName + " was caught trying to crash the server with an invalid position.";
    }

    public String getInvalidPositionKickMessage() {
        return "Nope!";
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
