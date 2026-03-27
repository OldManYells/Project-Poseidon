package com.legacyminecraft.poseidon.world;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Canonical behaviour for chunk tile-entity placement/removal lifecycle flow.
 */
public final class ChunkTileEntityLifecycleBehaviour {
    private static final ChunkTileEntityLifecycleBehaviour INSTANCE = new ChunkTileEntityLifecycleBehaviour();

    private ChunkTileEntityLifecycleBehaviour() {
    }

    public static ChunkTileEntityLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void placeTileEntity(Map tileEntities, Object world, int chunkX, int chunkZ,
                                int localX, int y, int localZ, int typeId, Object tileEntity) {
        ChunkPosition chunkPosition = new ChunkPosition(localX, y, localZ);

        setField(tileEntity, "world", world);
        setField(tileEntity, "x", chunkX * 16 + localX);
        setField(tileEntity, "y", y);
        setField(tileEntity, "z", chunkZ * 16 + localZ);

        if (typeId != 0) {
            invokeNoArg(tileEntity, "j");
            tileEntities.put(chunkPosition, tileEntity);
            return;
        }

        tileEntities.remove(chunkPosition);
    }

    public void removeTileEntityIfActive(Map tileEntities, boolean chunkActive, int localX, int y, int localZ) {
        if (!chunkActive) {
            return;
        }
        ChunkPosition chunkPosition = new ChunkPosition(localX, y, localZ);
        Object tileEntity = tileEntities.remove(chunkPosition);
        if (tileEntity != null) {
            invokeNoArg(tileEntity, "h");
        }
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

    private Object invokeNoArg(Object target, String methodName) {
        try {
            Method method = target.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
