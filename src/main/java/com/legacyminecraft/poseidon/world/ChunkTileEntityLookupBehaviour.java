package com.legacyminecraft.poseidon.world;


import java.lang.reflect.Method;
import java.util.Map;

/**
 * Canonical behaviour for chunk tile-entity lookup, lazy creation, and invalid-entry pruning.
 */
public final class ChunkTileEntityLookupBehaviour {
    private static final ChunkTileEntityLookupBehaviour INSTANCE = new ChunkTileEntityLookupBehaviour();

    private ChunkTileEntityLookupBehaviour() {
    }

    public static ChunkTileEntityLookupBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T resolveFromCache(Map tileEntities, ChunkPosition chunkPosition) {
        return (T) tileEntities.get(chunkPosition);
    }

    public boolean shouldCreateForType(int typeId) {
        return Block.isTileEntity[typeId];
    }

    public void createTileEntity(Object block, Object world, int x, int y, int z) {
        invokeBlockHook(block, "c", world, x, y, z);
    }

    @SuppressWarnings("unchecked")
    public <T> T pruneInvalidTileEntity(Map tileEntities, ChunkPosition chunkPosition, T tileEntity) {
        if (tileEntity != null && isInvalid(tileEntity)) {
            tileEntities.remove(chunkPosition);
            return null;
        }
        return tileEntity;
    }

    private boolean isInvalid(Object tileEntity) {
        return (Boolean) invoke(tileEntity, "g");
    }

    private void invokeBlockHook(Object target, String methodName, Object world, int x, int y, int z) {
        invoke(target, methodName, world, x, y, z);
    }

    private Object invoke(Object target, String methodName, Object... arguments) {
        try {
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != arguments.length) {
                    continue;
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                boolean compatible = true;
                for (int index = 0; index < parameterTypes.length; index++) {
                    Object argument = arguments[index];
                    if (!isCompatible(parameterTypes[index], argument)) {
                        compatible = false;
                        break;
                    }
                }

                if (!compatible) {
                    continue;
                }

                method.setAccessible(true);
                return method.invoke(target, arguments);
            }
            throw new NoSuchMethodException(methodName);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private boolean isCompatible(Class<?> parameterType, Object argument) {
        if (argument == null) {
            return !parameterType.isPrimitive();
        }
        if (!parameterType.isPrimitive()) {
            return parameterType.isAssignableFrom(argument.getClass());
        }
        if (parameterType == boolean.class) {
            return argument instanceof Boolean;
        }
        if (parameterType == byte.class) {
            return argument instanceof Byte;
        }
        if (parameterType == short.class) {
            return argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == int.class) {
            return argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == long.class) {
            return argument instanceof Long || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == float.class) {
            return argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == double.class) {
            return argument instanceof Double || argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == char.class) {
            return argument instanceof Character;
        }
        return false;
    }
}
