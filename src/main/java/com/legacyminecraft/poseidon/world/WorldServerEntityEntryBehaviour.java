package com.legacyminecraft.poseidon.world;

import java.lang.reflect.Field;

/**
 * Canonical behaviour for world-server entity-entry gating rules.
 */
public final class WorldServerEntityEntryBehaviour {
    private static final WorldServerEntityEntryBehaviour INSTANCE = new WorldServerEntityEntryBehaviour();

    private WorldServerEntityEntryBehaviour() {
    }

    public static WorldServerEntityEntryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldEnterWorld(Object entity) {
        Object passenger = readField(entity, "passenger");
        return passenger == null || !isEntityHuman(passenger);
    }

    private static boolean isEntityHuman(Object value) {
        Class<?> currentType = value.getClass();
        while (currentType != null) {
            if ("EntityHuman".equals(currentType.getSimpleName())) {
                return true;
            }
            currentType = currentType.getSuperclass();
        }
        return false;
    }

    private static Object readField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        try {
            Field field = readField(target.getClass(), fieldName);
            return field.get(target);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private static Field readField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> currentType = type;
        while (currentType != null) {
            try {
                Field field = currentType.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
                currentType = currentType.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
