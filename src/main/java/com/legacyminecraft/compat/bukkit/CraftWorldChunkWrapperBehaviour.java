package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld chunk-wrapper overload forwarding and wrapper rebinding.
 */
public final class CraftWorldChunkWrapperBehaviour {
    private static final CraftWorldChunkWrapperBehaviour INSTANCE = new CraftWorldChunkWrapperBehaviour();
    private static final ChunkWrapperProjectionBridgeBehaviour CHUNK_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            ChunkWrapperProjectionBridgeBehaviour.getInstance();

    private CraftWorldChunkWrapperBehaviour() {
    }

    public static CraftWorldChunkWrapperBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isChunkLoaded(Object craftWorld, Object requestedChunk) {
        int chunkX = intValue(invoke(requestedChunk, "getX"));
        int chunkZ = intValue(invoke(requestedChunk, "getZ"));
        Object loaded = invoke(craftWorld, "isChunkLoaded", Integer.TYPE, Integer.TYPE, Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
        return loaded instanceof Boolean && ((Boolean) loaded).booleanValue();
    }

    public boolean unloadChunk(Object craftWorld, Object requestedChunk) {
        int chunkX = intValue(invoke(requestedChunk, "getX"));
        int chunkZ = intValue(invoke(requestedChunk, "getZ"));
        Object unloaded = invoke(craftWorld, "unloadChunk", Integer.TYPE, Integer.TYPE, Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
        return unloaded instanceof Boolean && ((Boolean) unloaded).booleanValue();
    }

    public void loadChunk(Object craftWorld, Object requestedChunk) {
        int chunkX = intValue(invoke(requestedChunk, "getX"));
        int chunkZ = intValue(invoke(requestedChunk, "getZ"));
        invoke(craftWorld, "loadChunk", Integer.TYPE, Integer.TYPE, Integer.valueOf(chunkX), Integer.valueOf(chunkZ));
        invoke(
                CHUNK_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR,
                "bindLoadedBukkitChunk",
                Object.class,
                Object.class,
                craftWorld,
                requestedChunk
        );
    }

    private static Object invoke(Object target, String methodName) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
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

    private static int intValue(Object value) {
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }
}
