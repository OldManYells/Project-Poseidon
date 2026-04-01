package com.legacyminecraft.poseidon.modloader;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import com.legacyminecraft.poseidon.MinecraftServer;

import java.lang.reflect.Method;

/**
 * Compatibility shim for optional legacy ModLoader runtime.
 */
public final class ModLoader {

    private static final String LEGACY_MODLOADER_CLASS = "net.minecraft.server.ModLoader";

    private ModLoader() {
    }

    public static boolean isBackendPresent() {
        try {
            Class.forName(LEGACY_MODLOADER_CLASS);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public static void Init(MinecraftServer server) {
        invokeSingleArgumentStatic("Init", server);
    }

    public static void OnTick(MinecraftServer server) {
        invokeSingleArgumentStatic("OnTick", server);
    }

    private static void invokeSingleArgumentStatic(String methodName, Object argument) {
        final Class<?> backendClass = resolveBackendClass();
        final Method method = resolveSingleArgumentStaticMethod(backendClass, methodName, argument);

        try {
            method.invoke(null, argument);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke ModLoader method '" + methodName + "'", exception);
        }
    }

    private static Class<?> resolveBackendClass() {
        try {
            return Class.forName(LEGACY_MODLOADER_CLASS);
        } catch (ClassNotFoundException exception) {
            NoClassDefFoundError error = new NoClassDefFoundError(LEGACY_MODLOADER_CLASS);
            error.initCause(exception);
            throw error;
        }
    }

    private static Method resolveSingleArgumentStaticMethod(Class<?> backendClass, String methodName, Object argument) {
        Method fallbackMethod = null;

        for (Method method : backendClass.getMethods()) {
            if (!method.getName().equals(methodName) || method.getParameterCount() != 1) {
                continue;
            }

            final Class<?> parameterType = method.getParameterTypes()[0];
            if (argument != null && parameterType.isAssignableFrom(argument.getClass())) {
                return method;
            }

            if (fallbackMethod == null) {
                fallbackMethod = method;
            }
        }

        if (fallbackMethod != null) {
            return fallbackMethod;
        }

        throw new IllegalStateException("Missing ModLoader backend method '" + methodName + "(...)'");
    }
}
