package com.legacyminecraft.poseidon.world;


import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Canonical behaviour for chunk entity/tile-entity attach/detach lifecycle orchestration.
 */
public final class ChunkEntityLifecycleBehaviour {
    private static final ChunkEntityLifecycleBehaviour INSTANCE = new ChunkEntityLifecycleBehaviour();

    private ChunkEntityLifecycleBehaviour() {
    }

    public static ChunkEntityLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void addEntities(Object world, Map tileEntities, List[] entitySlices) {
        invokeSingleArgument(world, "a", tileEntities.values());
        for (int i = 0; i < entitySlices.length; ++i) {
            invokeSingleArgument(world, "a", entitySlices[i]);
        }
    }

    public void removeEntities(Object world, Map tileEntities, List[] entitySlices, int chunkX, int chunkZ,
                               PlayerSliceRemovalPolicy removalPolicy) {
        Iterator iterator = tileEntities.values().iterator();
        while (iterator.hasNext()) {
            Object tileEntity = iterator.next();
            invokeSingleArgument(world, "markForRemoval", tileEntity);
        }

        for (int i = 0; i < entitySlices.length; ++i) {
            Iterator<Object> sliceIterator = entitySlices[i].iterator();
            while (sliceIterator.hasNext()) {
                Object entity = sliceIterator.next();
                if (removalPolicy.shouldRemove(entity, chunkX, chunkZ)) {
                    sliceIterator.remove();
                }
            }
            invokeSingleArgument(world, "b", entitySlices[i]);
        }
    }

    public interface PlayerSliceRemovalPolicy {
        boolean shouldRemove(Object entity, int chunkX, int chunkZ);
    }

    private Object invokeSingleArgument(Object target, String methodName, Object argument) {
        try {
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != 1) {
                    continue;
                }

                Class<?> parameterType = method.getParameterTypes()[0];
                if (argument != null && !parameterType.isAssignableFrom(argument.getClass())) {
                    continue;
                }

                method.setAccessible(true);
                return method.invoke(target, argument);
            }
            throw new NoSuchMethodException(methodName);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
