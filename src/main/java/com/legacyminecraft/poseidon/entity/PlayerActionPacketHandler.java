package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.compat.bukkit.PlayerInteractEventBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

/**
 * Canonical handler for player action packets (arm animation, sneak state, bed leave).
 */
public final class PlayerActionPacketHandler {
    private static final PlayerActionPacketHandler INSTANCE = new PlayerActionPacketHandler();
    private final PlayerInteractEventBridgeBehaviour playerInteractEventBridge = PlayerInteractEventBridgeBehaviour.getInstance();

    private PlayerActionPacketHandler() {
    }

    public static PlayerActionPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleArmAnimationPacket(Object server, Object player, Object armAnimationPacket) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return;
        }

        int animation = ((Number) getField(armAnimationPacket, "b")).intValue();
        if (animation != 1) {
            return;
        }
        Object bukkitPlayer = invoke(player, "getBukkitEntity");
        Object event = LegacyCompatGatewayRegistry.gateway().createPlayerAnimationEvent(bukkitPlayer);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);
        if (Boolean.TRUE.equals(invoke(event, "isCancelled"))) {
            return;
        }
        invoke(player, "w");
    }

    public boolean handleEntityActionPacket(Object server, Object player, Object entityActionPacket) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return false;
        }

        int animation = ((Number) getField(entityActionPacket, "animation")).intValue();
        if (animation == 1 || animation == 2) {
            Object bukkitPlayer = invoke(player, "getBukkitEntity");
            Object event = LegacyCompatGatewayRegistry.gateway().createPlayerToggleSneakEvent(bukkitPlayer, animation == 1);
            Object pluginManager = invoke(server, "getPluginManager");
            invoke(pluginManager, "callEvent", event);

            if (Boolean.TRUE.equals(invoke(event, "isCancelled"))) {
                return false;
            }
        }

        if (animation == 1) {
            invoke(player, "setSneak", true);
        } else if (animation == 2) {
            invoke(player, "setSneak", false);
        } else if (shouldDisableMovementCheck(animation)) {
            invoke(player, "a", false, true, true);
            return true;
        }

        return false;
    }

    public boolean shouldDisableMovementCheck(int animation) {
        return animation == 3;
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
