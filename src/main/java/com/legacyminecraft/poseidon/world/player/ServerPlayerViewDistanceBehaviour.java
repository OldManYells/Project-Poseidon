package com.legacyminecraft.poseidon.world.player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Canonical policy for resolving furthest viewable block distance from server state.
 */
public final class ServerPlayerViewDistanceBehaviour {
    private static final ServerPlayerViewDistanceBehaviour INSTANCE = new ServerPlayerViewDistanceBehaviour();

    private ServerPlayerViewDistanceBehaviour() {
    }

    public static ServerPlayerViewDistanceBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveFurthestViewableBlock(MinecraftServer server) {
        List worlds = readListField(server, "worlds");
        if (worlds == null || worlds.isEmpty()) {
            Object propertyManager = readField(server, "propertyManager");
            Number distance = (Number) invoke(propertyManager, "getInt", "view-distance", Integer.valueOf(10));
            return distance.intValue() * 16 - 16;
        }

        Object world = worlds.get(0);
        Object manager = readField(world, "manager");
        Number furthestViewableBlock = (Number) invoke(manager, "getFurthestViewableBlock");
        return furthestViewableBlock.intValue();
    }

    private static List readListField(Object target, String fieldName) {
        Object value = readField(target, fieldName);
        return value instanceof List ? (List) value : null;
    }

    private static Object readField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        return null;
    }

    private static Object invoke(Object target, String methodName, Object... args) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            for (Method method : type.getDeclaredMethods()) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                } catch (Exception ignored) {
                    // Try another overload.
                }
            }
            type = type.getSuperclass();
        }

        throw new IllegalStateException("Method not found: " + methodName);
    }
}
