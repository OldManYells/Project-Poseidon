package com.legacyminecraft.poseidon.entity;

import java.lang.reflect.Method;

public final class FallingSandBehaviour {
    private static final FallingSandBehaviour INSTANCE = new FallingSandBehaviour();

    private FallingSandBehaviour() {
    }

    public static FallingSandBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSpawn(Object fallingSand, double spawnX, double spawnY, double spawnZ, int blockId) {
        setField(fallingSand, "a", blockId);
        setField(fallingSand, "aI", true);
        invoke(fallingSand, "poseidonInitializeBounds", new Class<?>[0]);
        invoke(fallingSand, "setPosition", new Class<?>[]{double.class, double.class, double.class}, spawnX, spawnY, spawnZ);
        setField(fallingSand, "motX", 0.0D);
        setField(fallingSand, "motY", 0.0D);
        setField(fallingSand, "motZ", 0.0D);
        setField(fallingSand, "lastX", spawnX);
        setField(fallingSand, "lastY", spawnY);
        setField(fallingSand, "lastZ", spawnZ);
    }

    public boolean shouldDieForMissingBlock(int blockId) {
        return blockId == 0;
    }

    public void tickPreMove(Object fallingSand) {
        setField(fallingSand, "lastX", getDouble(fallingSand, "locX"));
        setField(fallingSand, "lastY", getDouble(fallingSand, "locY"));
        setField(fallingSand, "lastZ", getDouble(fallingSand, "locZ"));
        setField(fallingSand, "b", getInt(fallingSand, "b") + 1);
        setField(fallingSand, "motY", getDouble(fallingSand, "motY") - 0.03999999910593033D);
    }

    public void tickPostMove(Object fallingSand) {
        setField(fallingSand, "motX", getDouble(fallingSand, "motX") * 0.9800000190734863D);
        setField(fallingSand, "motY", getDouble(fallingSand, "motY") * 0.9800000190734863D);
        setField(fallingSand, "motZ", getDouble(fallingSand, "motZ") * 0.9800000190734863D);
    }

    public BlockPos resolveBlockPos(double locX, double locY, double locZ) {
        return new BlockPos(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ));
    }

    public void clearSourceBlockIfMatching(Object fallingSand, int blockX, int blockY, int blockZ) {
        Object world = getField(fallingSand, "world");
        int blockId = getInt(fallingSand, "a");
        int worldType = ((Number) invoke(world, "getTypeId", new Class<?>[]{int.class, int.class, int.class}, blockX, blockY, blockZ)).intValue();
        if (worldType == blockId) {
            invoke(world, "setTypeId", new Class<?>[]{int.class, int.class, int.class, int.class}, blockX, blockY, blockZ, 0);
        }
    }

    public GroundImpactResult handleGroundImpact(Object fallingSand, int blockX, int blockY, int blockZ) {
        setField(fallingSand, "motX", getDouble(fallingSand, "motX") * 0.699999988079071D);
        setField(fallingSand, "motZ", getDouble(fallingSand, "motZ") * 0.699999988079071D);
        setField(fallingSand, "motY", getDouble(fallingSand, "motY") * -0.5D);

        Object world = getField(fallingSand, "world");
        int blockId = getInt(fallingSand, "a");
        boolean canPlace = (Boolean) invoke(world, "a", new Class<?>[]{int.class, int.class, int.class, int.class, boolean.class, int.class}, blockId, blockX, blockY, blockZ, true, 1);
        boolean placed = canPlace && (Boolean) invoke(world, "setRawTypeId", new Class<?>[]{int.class, int.class, int.class, int.class}, blockX, blockY, blockZ, blockId);
        boolean shouldDropItem = !placed && !(Boolean) getField(world, "isStatic");
        return new GroundImpactResult(shouldDropItem);
    }

    public boolean shouldDropForTimeout(int ageTicks, boolean worldStatic) {
        return ageTicks > 100 && !worldStatic;
    }

    public void writeTileNbt(Object nbt, int tileId) {
        invoke(nbt, "a", new Class<?>[]{String.class, byte.class}, "Tile", (byte) tileId);
    }

    public int readTileNbt(Object nbt) {
        return ((Number) invoke(nbt, "c", new Class<?>[]{String.class}, "Tile")).intValue() & 255;
    }

    private static Object invoke(Object target, String name, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = target.getClass().getMethod(name, parameterTypes);
            return method.invoke(target, args);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to invoke " + name, exception);
        }
    }

    private static Object getField(Object target, String name) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to read field " + name, exception);
        }
    }

    private static void setField(Object target, String name, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.set(target, value);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to write field " + name, exception);
        }
    }

    private static int getInt(Object target, String name) {
        return ((Number) getField(target, name)).intValue();
    }

    private static double getDouble(Object target, String name) {
        return ((Number) getField(target, name)).doubleValue();
    }

    public static final class BlockPos {
        public final int x;
        public final int y;
        public final int z;

        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class GroundImpactResult {
        public final boolean shouldDropItem;

        public GroundImpactResult(boolean shouldDropItem) {
            this.shouldDropItem = shouldDropItem;
        }
    }
}
