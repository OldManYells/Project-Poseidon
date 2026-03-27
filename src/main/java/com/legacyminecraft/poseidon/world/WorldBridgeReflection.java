package com.legacyminecraft.poseidon.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

final class WorldBridgeReflection {
    private WorldBridgeReflection() {
    }

    @SuppressWarnings("unchecked")
    static <T> T cast(Object value) {
        return (T) value;
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

    static Object invokeStatic(String className, String methodName, Object... args) {
        try {
            Class<?> type = Class.forName(className);
            for (Method method : type.getDeclaredMethods()) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }

                try {
                    method.setAccessible(true);
                    return method.invoke(null, args);
                } catch (Exception ignored) {
                    // try another overload
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke static method: " + methodName, exception);
        }
    }

    static Object construct(String className, Object... args) {
        try {
            Class<?> type = Class.forName(className);
            for (Constructor<?> constructor : type.getDeclaredConstructors()) {
                if (constructor.getParameterTypes().length != args.length) {
                    continue;
                }

                try {
                    constructor.setAccessible(true);
                    return constructor.newInstance(args);
                } catch (Exception ignored) {
                    // try another overload
                }
            }
            throw new IllegalStateException("Constructor not found: " + className);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to construct: " + className, exception);
        }
    }
}
