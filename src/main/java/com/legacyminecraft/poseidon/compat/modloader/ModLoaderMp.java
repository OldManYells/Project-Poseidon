package com.legacyminecraft.poseidon.compat.modloader;

import com.legacyminecraft.poseidon.EntityPlayer;

import java.lang.reflect.Method;

/**
 * Compatibility shim for optional legacy ModLoaderMP runtime.
 */
public final class ModLoaderMp {

    private static final String LEGACY_MODLOADER_MP_CLASS = "net.minecraft.server.ModLoaderMp";

    private ModLoaderMp() {
    }

    public static void HandleAllLogins(EntityPlayer player) {
        final Class<?> backendClass = resolveBackendClass();
        final Method method = resolveSingleArgumentStaticMethod(backendClass, "HandleAllLogins", player);

        try {
            method.invoke(null, player);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke ModLoaderMp.HandleAllLogins(...)", exception);
        }
    }

    private static Class<?> resolveBackendClass() {
        try {
            return Class.forName(LEGACY_MODLOADER_MP_CLASS);
        } catch (ClassNotFoundException exception) {
            NoClassDefFoundError error = new NoClassDefFoundError(LEGACY_MODLOADER_MP_CLASS);
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

        throw new IllegalStateException("Missing ModLoaderMp backend method '" + methodName + "(...)'");
    }
}
