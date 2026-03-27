package com.legacyminecraft.poseidon.world;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Canonical behaviour for chunk-local entity query range and slice scans.
 */
public final class ChunkEntityQueryBehaviour {
    private static final ChunkEntityQueryBehaviour INSTANCE = new ChunkEntityQueryBehaviour();

    private ChunkEntityQueryBehaviour() {
    }

    public static ChunkEntityQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public SliceBounds resolveSliceBounds(Object box, int sliceCount) {
        int minSlice = MathHelper.floor((getDoubleField(box, "b") - 2.0D) / 16.0D);
        int maxSlice = MathHelper.floor((getDoubleField(box, "e") + 2.0D) / 16.0D);
        if (minSlice < 0) {
            minSlice = 0;
        }
        if (maxSlice >= sliceCount) {
            maxSlice = sliceCount - 1;
        }
        return new SliceBounds(minSlice, maxSlice);
    }

    public void collectCollidingExcluding(Object excluded, Object box, List[] entitySlices, List out, SliceBounds bounds) {
        for (int slice = bounds.minSlice; slice <= bounds.maxSlice; ++slice) {
            List entities = entitySlices[slice];
            for (int i = 0; i < entities.size(); ++i) {
                Object candidate = entities.get(i);
                if (candidate != excluded && intersects(candidate, box)) {
                    out.add(candidate);
                }
            }
        }
    }

    public void collectAssignableColliding(Class type, Object box, List[] entitySlices, List out, SliceBounds bounds) {
        for (int slice = bounds.minSlice; slice <= bounds.maxSlice; ++slice) {
            List entities = entitySlices[slice];
            for (int i = 0; i < entities.size(); ++i) {
                Object candidate = entities.get(i);
                if (type.isAssignableFrom(candidate.getClass()) && intersects(candidate, box)) {
                    out.add(candidate);
                }
            }
        }
    }

    private boolean intersects(Object candidate, Object box) {
        Object boundingBox = getField(candidate, "boundingBox");
        return (Boolean) invoke(boundingBox, "a", box);
    }

    private double getDoubleField(Object target, String fieldName) {
        Object value = getField(target, fieldName);
        return ((Number) value).doubleValue();
    }

    private Object getField(Object target, String fieldName) {
        try {
            Field field = findField(target.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
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
                    if (argument != null && !parameterTypes[index].isAssignableFrom(argument.getClass())) {
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

    public static final class SliceBounds {
        public final int minSlice;
        public final int maxSlice;

        public SliceBounds(int minSlice, int maxSlice) {
            this.minSlice = minSlice;
            this.maxSlice = maxSlice;
        }
    }
}
