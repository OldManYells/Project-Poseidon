package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Canonical behaviour for world-server weather-state packet fan-out.
 */
public final class WorldServerWeatherBroadcastBehaviour {
    private static final WorldServerWeatherBroadcastBehaviour INSTANCE = new WorldServerWeatherBroadcastBehaviour();

    private WorldServerWeatherBroadcastBehaviour() {
    }

    public static WorldServerWeatherBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public void broadcastWeatherStateChange(List players, Object world, int weatherPacketType) {
        Object packet = createWeatherPacket(weatherPacketType);
        if (packet == null) {
            return;
        }

        for (int playerIndex = 0; playerIndex < players.size(); ++playerIndex) {
            Object entityPlayer = players.get(playerIndex);
            if (readField(entityPlayer, "world") == world) {
                Object netServerHandler = readField(entityPlayer, "netServerHandler");
                invokeOneArgument(netServerHandler, "sendPacket", packet);
            }
        }
    }

    private static Object createWeatherPacket(int weatherPacketType) {
        return LegacyCompatGatewayRegistry.gateway().createPacket70Bed(weatherPacketType);
    }

    private static Object readField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        try {
            Field field = readField(target.getClass(), fieldName);
            return field.get(target);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private static void invokeOneArgument(Object target, String methodName, Object argument) {
        if (target == null || argument == null) {
            return;
        }

        try {
            Method method = findCompatibleMethod(target.getClass(), methodName, argument.getClass());
            if (method == null) {
                throw new NoSuchMethodException(methodName);
            }
            method.invoke(target, argument);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to invoke method: " + methodName, exception);
        }
    }

    private static Method findCompatibleMethod(Class<?> type, String methodName, Class<?> argumentType) {
        for (Method method : type.getMethods()) {
            if (!method.getName().equals(methodName) || method.getParameterTypes().length != 1) {
                continue;
            }

            Class<?> parameterType = method.getParameterTypes()[0];
            if (parameterType.isAssignableFrom(argumentType) || parameterType == Object.class) {
                return method;
            }
        }
        return null;
    }

    private static Field readField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> currentType = type;
        while (currentType != null) {
            try {
                Field field = currentType.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
                currentType = currentType.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
