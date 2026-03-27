package org.bukkit.craftbukkit.util;

import com.legacyminecraft.compat.bukkit.JavaArrayCopyBehaviour;

public class Java15Compat {
    private static final JavaArrayCopyBehaviour JAVA_ARRAY_COPY_BEHAVIOUR = JavaArrayCopyBehaviour.getInstance();

    public static <T> T[] Arrays_copyOf(T[] original, int newLength) {
        return JAVA_ARRAY_COPY_BEHAVIOUR.copyOf(original, newLength);
    }

    public static long[] Arrays_copyOf(long[] original, int newLength) {
        return JAVA_ARRAY_COPY_BEHAVIOUR.copyOf(original, newLength);
    }

}
