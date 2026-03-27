package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunk world lifecycle delegation.
 */
public final class CraftChunkLifecycleBehaviour {
    private static final CraftChunkLifecycleBehaviour INSTANCE = new CraftChunkLifecycleBehaviour();

    private CraftChunkLifecycleBehaviour() {
    }

    public static CraftChunkLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isLoaded(Object world, int chunkX, int chunkZ) {
        Object loaded = invoke(world, "isChunkLoaded", Integer.TYPE, Integer.TYPE, Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
        return loaded instanceof Boolean && ((Boolean) loaded).booleanValue();
    }

    public boolean load(Object world, int chunkX, int chunkZ, boolean generate) {
        Object loaded = invoke(
                world,
                "loadChunk",
                new Class<?>[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE},
                new Object[]{Integer.valueOf(chunkX), Integer.valueOf(chunkZ), Boolean.valueOf(generate)}
        );
        return loaded instanceof Boolean && ((Boolean) loaded).booleanValue();
    }

    public boolean unload(Object world, int chunkX, int chunkZ) {
        Object unloaded = invoke(world, "unloadChunk", Integer.TYPE, Integer.TYPE, Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
        return unloaded instanceof Boolean && ((Boolean) unloaded).booleanValue();
    }

    public boolean unload(Object world, int chunkX, int chunkZ, boolean save) {
        Object unloaded = invoke(
                world,
                "unloadChunk",
                new Class<?>[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE},
                new Object[]{Integer.valueOf(chunkX), Integer.valueOf(chunkZ), Boolean.valueOf(save)}
        );
        return unloaded instanceof Boolean && ((Boolean) unloaded).booleanValue();
    }

    public boolean unload(Object world, int chunkX, int chunkZ, boolean save, boolean safe) {
        Object unloaded = invoke(
                world,
                "unloadChunk",
                new Class<?>[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE},
                new Object[]{Integer.valueOf(chunkX), Integer.valueOf(chunkZ), Boolean.valueOf(save), Boolean.valueOf(safe)}
        );
        return unloaded instanceof Boolean && ((Boolean) unloaded).booleanValue();
    }

    private static Object invoke(Object target, String methodName, Class<?> argType0, Class<?> argType1, Object arg0, Object arg1) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(methodName, argType0, argType1).invoke(target, arg0, arg1);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(methodName, parameterTypes).invoke(target, arguments);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
