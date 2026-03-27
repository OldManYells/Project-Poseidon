package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld empty chunk snapshot bridge wrapper glue.
 */
public final class CraftWorldEmptyChunkSnapshotBridgeBehaviour {
    private static final CraftWorldEmptyChunkSnapshotBridgeBehaviour INSTANCE =
            new CraftWorldEmptyChunkSnapshotBridgeBehaviour();

    private CraftWorldEmptyChunkSnapshotBridgeBehaviour() {
    }

    public static CraftWorldEmptyChunkSnapshotBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T getEmptyChunkSnapshot(int x, int z, Object world, boolean includeBiome, boolean includeBiomeTempRain) {
        if (world == null) {
            return null;
        }
        try {
            Class<?> chunkType = resolveCraftChunkType(world);
            Object snapshot = invokeStaticChunkSnapshotMethod(
                    chunkType,
                    Integer.valueOf(x),
                    Integer.valueOf(z),
                    world,
                    Boolean.valueOf(includeBiome),
                    Boolean.valueOf(includeBiomeTempRain)
            );
            return (T) snapshot;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Class<?> resolveCraftChunkType(Object world) throws ClassNotFoundException {
        String worldClassName = world.getClass().getName();
        int split = worldClassName.lastIndexOf('.');
        String packageName = split >= 0 ? worldClassName.substring(0, split) : "";
        String chunkClassName = packageName.isEmpty() ? "CraftChunk" : packageName + ".CraftChunk";
        return world.getClass().getClassLoader().loadClass(chunkClassName);
    }

    private static Object invokeStaticChunkSnapshotMethod(
            Class<?> chunkType,
            Object x,
            Object z,
            Object world,
            Object includeBiome,
            Object includeBiomeTempRain
    ) throws ReflectiveOperationException {
        java.lang.reflect.Method[] methods = chunkType.getMethods();
        for (int index = 0; index < methods.length; index++) {
            java.lang.reflect.Method method = methods[index];
            if (!"getEmptyChunkSnapshot".equals(method.getName())) {
                continue;
            }
            if (method.getParameterTypes().length != 5) {
                continue;
            }
            return method.invoke(null, x, z, world, includeBiome, includeBiomeTempRain);
        }
        return null;
    }
}
