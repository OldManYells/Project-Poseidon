package com.legacyminecraft.poseidon.world;


import java.lang.reflect.Field;
import java.util.List;

/**
 * Canonical behaviour for resolving chunk-local entity slice indices and slice membership updates.
 */
public final class ChunkEntitySliceIndexBehaviour {
    private static final ChunkEntitySliceIndexBehaviour INSTANCE = new ChunkEntitySliceIndexBehaviour();

    private ChunkEntitySliceIndexBehaviour() {
    }

    public static ChunkEntitySliceIndexBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveChunkCoordinate(double coordinate) {
        return MathHelper.floor(coordinate / 16.0D);
    }

    public int resolveSliceIndex(double y, int sliceCount) {
        int sliceIndex = MathHelper.floor(y / 16.0D);
        if (sliceIndex < 0) {
            return 0;
        }
        if (sliceIndex >= sliceCount) {
            return sliceCount - 1;
        }
        return sliceIndex;
    }

    public void markAndAdd(Object entity, int chunkX, int chunkZ, int sliceIndex, List[] entitySlices) {
        setField(entity, "bG", true);
        setField(entity, "bH", chunkX);
        setField(entity, "bI", sliceIndex);
        setField(entity, "bJ", chunkZ);
        entitySlices[sliceIndex].add(entity);
    }

    public int clampSliceIndex(int sliceIndex, int sliceCount) {
        if (sliceIndex < 0) {
            return 0;
        }
        if (sliceIndex >= sliceCount) {
            return sliceCount - 1;
        }
        return sliceIndex;
    }

    public void remove(Object entity, int sliceIndex, List[] entitySlices) {
        entitySlices[sliceIndex].remove(entity);
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = findField(target.getClass(), fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Field findField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
