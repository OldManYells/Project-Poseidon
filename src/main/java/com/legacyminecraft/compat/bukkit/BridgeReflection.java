package com.legacyminecraft.compat.bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

final class BridgeReflection {
    private BridgeReflection() {
    }

    @SuppressWarnings("unchecked")
    static <T> T cast(Object value) {
        return (T) value;
    }

    static Object getField(Object target, String fieldName) {
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
                throw new IllegalStateException("Unable to read field: " + fieldName, exception);
            }
        }
        throw new IllegalStateException("Field not found: " + fieldName);
    }

    static void setField(Object target, String fieldName, Object value) {
        if (target == null) {
            return;
        }
        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to write field: " + fieldName, exception);
            }
        }
        throw new IllegalStateException("Field not found: " + fieldName);
    }

    static Object invoke(Object target, String methodName, Object... args) {
        if (target == null) {
            return null;
        }
        Class<?> type = target.getClass();
        while (type != null) {
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                } catch (Exception ignored) {
                    // try another overload
                }
            }
            type = type.getSuperclass();
        }
        throw new IllegalStateException("Method not found: " + methodName);
    }
}
