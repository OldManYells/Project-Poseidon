package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class WorldAccessDispatchBehaviour {
    private static final WorldAccessDispatchBehaviour INSTANCE = new WorldAccessDispatchBehaviour();

    private WorldAccessDispatchBehaviour() {
    }

    public static WorldAccessDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void onEntityAdded(Object server, Object world, Object entity) {
        invoke(invoke(server, "getTracker", Integer.valueOf(getIntField(world, "dimension"))), "track", entity);
    }

    public void onEntityRemoved(Object server, Object world, Object entity) {
        invoke(invoke(server, "getTracker", Integer.valueOf(getIntField(world, "dimension"))), "untrackEntity", entity);
    }

    public void markBlockDirty(Object server, Object world, int i, int j, int k) {
        invoke(getField(server, "serverConfigurationManager"), "flagDirty",
                Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(getIntField(world, "dimension")));
    }

    public void onTileEntityChanged(Object server, int i, int j, int k, Object tileentity) {
        invoke(getField(server, "serverConfigurationManager"), "a",
                Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), tileentity);
    }

    public void sendAuxSfx(Object server, Object world, Object entityhuman, int i, int j, int k, int l, int i1) {
        Object packet = LegacyCompatGatewayRegistry.gateway().createPacket61(i, j, k, l, i1);
        invoke(getField(server, "serverConfigurationManager"), "sendPacketNearby",
                entityhuman,
                Double.valueOf(j),
                Double.valueOf(k),
                Double.valueOf(l),
                Double.valueOf(64.0D),
                Integer.valueOf(getIntField(world, "dimension")),
                packet);
    }

    private Object getField(Object target, String fieldName) {
        try {
            Field field = findField(target.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private int getIntField(Object target, String fieldName) {
        try {
            Field field = findField(target.getClass(), fieldName);
            field.setAccessible(true);
            return field.getInt(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Field findField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private Object invoke(Object target, String methodName, Object... arguments) {
        try {
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != arguments.length) {
                    continue;
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                boolean compatible = true;
                for (int index = 0; index < parameterTypes.length; index++) {
                    Object argument = arguments[index];
                    if (!isCompatible(parameterTypes[index], argument)) {
                        compatible = false;
                        break;
                    }
                }

                if (!compatible) {
                    continue;
                }

                method.setAccessible(true);
                return method.invoke(target, arguments);
            }
            throw new NoSuchMethodException(methodName);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private boolean isCompatible(Class<?> parameterType, Object argument) {
        if (argument == null) {
            return !parameterType.isPrimitive();
        }
        if (!parameterType.isPrimitive()) {
            return parameterType.isAssignableFrom(argument.getClass());
        }
        if (parameterType == boolean.class) {
            return argument instanceof Boolean;
        }
        if (parameterType == byte.class) {
            return argument instanceof Byte;
        }
        if (parameterType == short.class) {
            return argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == int.class) {
            return argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == long.class) {
            return argument instanceof Long || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == float.class) {
            return argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == double.class) {
            return argument instanceof Double || argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == char.class) {
            return argument instanceof Character;
        }
        return false;
    }
}
