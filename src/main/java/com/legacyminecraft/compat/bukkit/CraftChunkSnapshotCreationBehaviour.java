package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunk snapshot object construction.
 */
public final class CraftChunkSnapshotCreationBehaviour {
    private static final CraftChunkSnapshotCreationBehaviour INSTANCE = new CraftChunkSnapshotCreationBehaviour();

    private CraftChunkSnapshotCreationBehaviour() {
    }

    public static CraftChunkSnapshotCreationBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T createSnapshot(
            int chunkX,
            int chunkZ,
            Object world,
            ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData
    ) {
        if (world == null) {
            return null;
        }
        try {
            Class<?> snapshotType = resolveChunkSnapshotType(world);
            Object snapshot = snapshotType.getConstructor(
                    Integer.TYPE,
                    Integer.TYPE,
                    String.class,
                    Long.TYPE,
                    byte[].class,
                    byte[].class,
                    byte[].class,
                    double[].class,
                    double[].class
            ).newInstance(
                    Integer.valueOf(chunkX),
                    Integer.valueOf(chunkZ),
                    stringValue(invoke(world, "getName")),
                    Long.valueOf(longValue(invoke(world, "getFullTime"))),
                    snapshotData.getChunkBuffer(),
                    snapshotData.getHeightMap(),
                    snapshotData.getBiomes(),
                    snapshotData.getTemperatures(),
                    snapshotData.getRainfall()
            );
            return (T) snapshot;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Class<?> resolveChunkSnapshotType(Object world) throws ClassNotFoundException {
        String worldClassName = world.getClass().getName();
        int split = worldClassName.lastIndexOf('.');
        String packageName = split >= 0 ? worldClassName.substring(0, split) : "";
        String snapshotClassName = packageName.isEmpty() ? "CraftChunkSnapshot" : packageName + ".CraftChunkSnapshot";
        return world.getClass().getClassLoader().loadClass(snapshotClassName);
    }

    private static Object invoke(Object target, String methodName) {
        try {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static long longValue(Object value) {
        return value instanceof Number ? ((Number) value).longValue() : 0L;
    }

    private static String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
