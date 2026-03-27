package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

public final class MovingObjectPositionBehaviour {
    private static final MovingObjectPositionBehaviour INSTANCE = new MovingObjectPositionBehaviour();

    private MovingObjectPositionBehaviour() {
    }

    public static MovingObjectPositionBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeTileHit(Object target, int x, int y, int z, int face, Object hitVector) {
        Reflection.setField(target, "type", LegacyCompatGatewayRegistry.gateway().enumMovingObjectTypeTile());
        Reflection.setField(target, "b", x);
        Reflection.setField(target, "c", y);
        Reflection.setField(target, "d", z);
        Reflection.setField(target, "face", face);
        Reflection.setField(
                target,
                "f",
                LegacyCompatGatewayRegistry.gateway().createVec3(
                        toDouble(Reflection.getField(hitVector, "a")),
                        toDouble(Reflection.getField(hitVector, "b")),
                        toDouble(Reflection.getField(hitVector, "c"))
                )
        );
    }

    public void initializeEntityHit(Object target, Object entity) {
        Reflection.setField(target, "type", LegacyCompatGatewayRegistry.gateway().enumMovingObjectTypeEntity());
        Reflection.setField(target, "entity", entity);
        Reflection.setField(
                target,
                "f",
                LegacyCompatGatewayRegistry.gateway().createVec3(
                        toDouble(Reflection.getField(entity, "locX")),
                        toDouble(Reflection.getField(entity, "locY")),
                        toDouble(Reflection.getField(entity, "locZ"))
                )
        );
    }

    private static double toDouble(Object value) {
        return value == null ? 0.0D : ((Number) value).doubleValue();
    }

    private static final class Reflection {
        private Reflection() {
        }

        static Object getField(Object target, String name) {
            Class<?> type = target.getClass();
            while (type != null) {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(target);
                } catch (NoSuchFieldException ignored) {
                    type = type.getSuperclass();
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to read field: " + name, exception);
                }
            }
            throw new IllegalStateException("Field not found: " + name);
        }

        static void setField(Object target, String name, Object value) {
            Class<?> type = target.getClass();
            while (type != null) {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    field.set(target, value);
                    return;
                } catch (NoSuchFieldException ignored) {
                    type = type.getSuperclass();
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to write field: " + name, exception);
                }
            }
            throw new IllegalStateException("Field not found: " + name);
        }

    }
}
