package com.legacyminecraft.poseidon.network;


/**
 * Canonical helper for movement packet handling while the player is sleeping.
 */
public final class SleepingMovementPacketHandler {
    private static final SleepingMovementPacketHandler INSTANCE = new SleepingMovementPacketHandler();

    private SleepingMovementPacketHandler() {
    }

    public static SleepingMovementPacketHandler getInstance() {
        return INSTANCE;
    }

    public boolean handleSleepingMovement(
            Object player,
            Object worldServer,
            double lockedX,
            double lockedY,
            double lockedZ
    ) {
        if (!shouldHandleSleepingMovement(Boolean.TRUE.equals(invoke(player, "isSleeping")))) {
            return false;
        }

        invoke(player, "a", true);
        float yaw = ((Number) getField(player, "yaw")).floatValue();
        float pitch = ((Number) getField(player, "pitch")).floatValue();
        invoke(player, "setLocation", lockedX, lockedY, lockedZ, yaw, pitch);
        invoke(worldServer, "playerJoinedWorld", player);
        return true;
    }

    public boolean shouldHandleSleepingMovement(boolean sleeping) {
        return sleeping;
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
}
