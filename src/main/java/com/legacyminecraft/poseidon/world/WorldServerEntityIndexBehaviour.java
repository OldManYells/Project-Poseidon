package com.legacyminecraft.poseidon.world;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Canonical behaviour for world-server entity-id index bookkeeping.
 */
public final class WorldServerEntityIndexBehaviour {
    private static final WorldServerEntityIndexBehaviour INSTANCE = new WorldServerEntityIndexBehaviour();

    private WorldServerEntityIndexBehaviour() {
    }

    public static WorldServerEntityIndexBehaviour getInstance() {
        return INSTANCE;
    }

    public void indexEntity(Object entityIndex, Object entity) {
        invoke(entityIndex, "a", int.class, Object.class, readInt(entity, "id"), entity);
    }

    public void unindexEntity(Object entityIndex, Object entity) {
        invoke(entityIndex, "d", int.class, readInt(entity, "id"));
    }

    public Object getById(Object entityIndex, int entityId) {
        return invoke(entityIndex, "a", int.class, entityId);
    }

    private static int readInt(Object target, String fieldName) {
        if (target == null) {
            return 0;
        }
        try {
            Field field = readField(target.getClass(), fieldName);
            return field.getInt(target);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private static Object invoke(Object target, String methodName, Class<?> parameterType, Object argument) {
        return invoke(target, methodName, new Class<?>[] {parameterType}, new Object[] {argument});
    }

    private static Object invoke(Object target, String methodName, Class<?> firstParameterType, Class<?> secondParameterType, Object firstArgument, Object secondArgument) {
        return invoke(
                target,
                methodName,
                new Class<?>[] {firstParameterType, secondParameterType},
                new Object[] {firstArgument, secondArgument}
        );
    }

    private static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        if (target == null) {
            return null;
        }
        try {
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(target, arguments);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to invoke method: " + methodName, exception);
        }
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
