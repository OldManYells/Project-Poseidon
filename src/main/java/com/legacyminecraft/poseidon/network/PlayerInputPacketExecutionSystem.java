package com.legacyminecraft.poseidon.network;


/**
 * Canonical execution flow for packet 27 player input forwarding.
 */
public final class PlayerInputPacketExecutionSystem {
    private static final PlayerInputPacketExecutionSystem INSTANCE = new PlayerInputPacketExecutionSystem();

    private PlayerInputPacketExecutionSystem() {
    }

    public static PlayerInputPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Object packet27, InputActions inputActions) {
        inputActions.applyInput(
                ((Number) invoke(packet27, "c")).floatValue(),
                ((Number) invoke(packet27, "e")).floatValue(),
                Boolean.TRUE.equals(invoke(packet27, "g")),
                Boolean.TRUE.equals(invoke(packet27, "h")),
                ((Number) invoke(packet27, "d")).floatValue(),
                ((Number) invoke(packet27, "f")).floatValue()
        );
    }

    public interface InputActions {
        void applyInput(float primaryX, float primaryY, boolean primaryFlag, boolean secondaryFlag, float secondaryX, float secondaryY);
    }

    private Object invoke(Object target, String methodName) {
        try {
            java.lang.reflect.Method method = target.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }
}
