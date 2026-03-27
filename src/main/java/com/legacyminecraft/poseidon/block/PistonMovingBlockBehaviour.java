package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

/**
 * Canonical behavior service for moving-piston block wrappers.
 */
public final class PistonMovingBlockBehaviour {
    private static final PistonMovingBlockBehaviour INSTANCE = new PistonMovingBlockBehaviour();

    private PistonMovingBlockBehaviour() {
    }

    public static PistonMovingBlockBehaviour getInstance() {
        return INSTANCE;
    }

    public Object createMovingTileEntity(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead) {
        return LegacyCompatGatewayRegistry.gateway()
                .createMovingTileEntityPiston(movedBlockId, movedBlockData, facing, extending, renderHead);
    }

    public <T> T extractPistonTileEntity(Object tileEntity) {
        if (tileEntity == null) {
            return null;
        }
        if (LegacyCompatGatewayRegistry.gateway().isTileEntityPiston(tileEntity)) {
            @SuppressWarnings("unchecked")
            T cast = (T) tileEntity;
            return cast;
        }
        return null;
    }

    public boolean handleRemove(Object world, int x, int y, int z) {
        Object tileEntity = Reflection.invoke(world, "getTileEntity", x, y, z);
        Object tileEntityPiston = extractPistonTileEntity(tileEntity);
        if (tileEntityPiston == null) {
            return false;
        }
        Reflection.invoke(tileEntityPiston, "k");
        return true;
    }

    public boolean shouldClearOrphanMovingBlock(Object world, int x, int y, int z) {
        boolean isStatic = (Boolean) Reflection.getField(world, "isStatic");
        Object tile = Reflection.invoke(world, "getTileEntity", x, y, z);
        return !isStatic && tile == null;
    }

    public void dropMovedBlockNaturally(Object world, int x, int y, int z, Object tileEntityPiston) {
        boolean isStatic = (Boolean) Reflection.getField(world, "isStatic");
        if (isStatic || tileEntityPiston == null) {
            return;
        }

        int movedBlockId = ((Number) Reflection.invoke(tileEntityPiston, "a")).intValue();
        int movedData = ((Number) Reflection.invoke(tileEntityPiston, "e")).intValue();
        Object block = LegacyCompatGatewayRegistry.gateway().blockById(movedBlockId);
        Reflection.invoke(block, "g", world, x, y, z, movedData);
    }

    public float resolveRenderProgress(Object tileEntityPiston) {
        float progress = ((Number) Reflection.invoke(tileEntityPiston, "a", 0.0F)).floatValue();
        boolean extending = (Boolean) Reflection.invoke(tileEntityPiston, "c");
        return extending ? 1.0F - progress : progress;
    }

    public <T> T resolveShiftedCollisionBox(Object world, int x, int y, int z, int movedBlockId, float progress, int facing, int movingBlockId) {
        if (movedBlockId == 0 || movedBlockId == movingBlockId) {
            return null;
        }

        Object movedBlock = LegacyCompatGatewayRegistry.gateway().blockById(movedBlockId);
        Object axisAlignedBB = Reflection.invoke(movedBlock, "e", world, x, y, z);
        if (axisAlignedBB == null) {
            return null;
        }

        int[] b = LegacyCompatGatewayRegistry.gateway().pistonOffsetX();
        int[] c = LegacyCompatGatewayRegistry.gateway().pistonOffsetY();
        int[] d = LegacyCompatGatewayRegistry.gateway().pistonOffsetZ();

        shiftBoundingField(axisAlignedBB, "a", b[facing], progress);
        shiftBoundingField(axisAlignedBB, "d", b[facing], progress);
        shiftBoundingField(axisAlignedBB, "b", c[facing], progress);
        shiftBoundingField(axisAlignedBB, "e", c[facing], progress);
        shiftBoundingField(axisAlignedBB, "c", d[facing], progress);
        shiftBoundingField(axisAlignedBB, "f", d[facing], progress);

        @SuppressWarnings("unchecked")
        T cast = (T) axisAlignedBB;
        return cast;
    }

    public Bounds resolveShiftedOutlineBounds(Object movedBlock, float progress, int facing) {
        int[] b = LegacyCompatGatewayRegistry.gateway().pistonOffsetX();
        int[] c = LegacyCompatGatewayRegistry.gateway().pistonOffsetY();
        int[] d = LegacyCompatGatewayRegistry.gateway().pistonOffsetZ();

        return new Bounds(
                ((Number) Reflection.getField(movedBlock, "minX")).doubleValue() - (double) ((float) b[facing] * progress),
                ((Number) Reflection.getField(movedBlock, "minY")).doubleValue() - (double) ((float) c[facing] * progress),
                ((Number) Reflection.getField(movedBlock, "minZ")).doubleValue() - (double) ((float) d[facing] * progress),
                ((Number) Reflection.getField(movedBlock, "maxX")).doubleValue() - (double) ((float) b[facing] * progress),
                ((Number) Reflection.getField(movedBlock, "maxY")).doubleValue() - (double) ((float) c[facing] * progress),
                ((Number) Reflection.getField(movedBlock, "maxZ")).doubleValue() - (double) ((float) d[facing] * progress)
        );
    }

    private static void shiftBoundingField(Object axisAlignedBB, String fieldName, int axisDelta, float progress) {
        double current = ((Number) Reflection.getField(axisAlignedBB, fieldName)).doubleValue();
        Reflection.setField(axisAlignedBB, fieldName, current - (double) ((float) axisDelta * progress));
    }

    public static final class Bounds {
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;

        public Bounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public double getMinX() { return minX; }
        public double getMinY() { return minY; }
        public double getMinZ() { return minZ; }
        public double getMaxX() { return maxX; }
        public double getMaxY() { return maxY; }
        public double getMaxZ() { return maxZ; }
    }

    private static final class Reflection {
        private Reflection() {
        }

        static Object getStaticField(Class<?> type, String name) {
            try {
                java.lang.reflect.Field field = type.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(null);
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to read static field: " + name, exception);
            }
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

        static Object invoke(Object target, String methodName, Object... args) {
            Class<?> type = target.getClass();
            while (type != null) {
                for (java.lang.reflect.Method method : type.getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        try {
                            method.setAccessible(true);
                            return method.invoke(target, args);
                        } catch (Exception ignored) {
                        }
                    }
                }
                for (java.lang.reflect.Method method : type.getDeclaredMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        try {
                            method.setAccessible(true);
                            return method.invoke(target, args);
                        } catch (Exception ignored) {
                        }
                    }
                }
                type = type.getSuperclass();
            }
            throw new IllegalStateException("Method not found: " + methodName);
        }
    }
}
