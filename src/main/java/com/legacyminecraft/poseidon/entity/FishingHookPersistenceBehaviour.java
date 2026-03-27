package com.legacyminecraft.poseidon.entity;


/**
 * Canonical NBT persistence behaviour for fishing hook entity state.
 */
public final class FishingHookPersistenceBehaviour {
    private static final FishingHookPersistenceBehaviour INSTANCE = new FishingHookPersistenceBehaviour();

    private FishingHookPersistenceBehaviour() {
    }

    public static FishingHookPersistenceBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeToNbt(Object nbt,
                           int blockX,
                           int blockY,
                           int blockZ,
                           int inTileId,
                           int shakeTicks,
                           boolean inGround) {
        invokeNbtSet(nbt, "xTile", Short.valueOf((short) blockX));
        invokeNbtSet(nbt, "yTile", Short.valueOf((short) blockY));
        invokeNbtSet(nbt, "zTile", Short.valueOf((short) blockZ));
        invokeNbtSet(nbt, "inTile", Byte.valueOf((byte) inTileId));
        invokeNbtSet(nbt, "shake", Byte.valueOf((byte) shakeTicks));
        invokeNbtSet(nbt, "inGround", Byte.valueOf((byte) (inGround ? 1 : 0)));
    }

    public HookPersistenceState readFromNbt(Object nbt) {
        int blockX = readShortTag(nbt, "xTile");
        int blockY = readShortTag(nbt, "yTile");
        int blockZ = readShortTag(nbt, "zTile");
        int inTileId = readByteTag(nbt, "inTile") & 255;
        int shakeTicks = readByteTag(nbt, "shake") & 255;
        boolean inGround = readByteTag(nbt, "inGround") == 1;

        return new HookPersistenceState(blockX, blockY, blockZ, inTileId, shakeTicks, inGround);
    }

    private void invokeNbtSet(Object nbt, String key, Object value) {
        if (nbt == null) {
            return;
        }
        try {
            Class<?> nbtClass = nbt.getClass();
            Class<?> valueClass = value.getClass();
            nbtClass.getMethod("a", String.class, valueClass).invoke(nbt, key, value);
        } catch (ReflectiveOperationException ignored) {
            // Keep wrapper compatibility non-fatal if a legacy implementation differs.
        }
    }

    private int readShortTag(Object nbt, String key) {
        if (nbt == null) {
            return 0;
        }
        try {
            Object value = nbt.getClass().getMethod("d", String.class).invoke(nbt, key);
            return ((Number) value).intValue();
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private int readByteTag(Object nbt, String key) {
        if (nbt == null) {
            return 0;
        }
        try {
            Object value = nbt.getClass().getMethod("c", String.class).invoke(nbt, key);
            return ((Number) value).intValue();
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    public static final class HookPersistenceState {
        private final int blockX;
        private final int blockY;
        private final int blockZ;
        private final int inTileId;
        private final int shakeTicks;
        private final boolean inGround;

        public HookPersistenceState(int blockX, int blockY, int blockZ, int inTileId, int shakeTicks, boolean inGround) {
            this.blockX = blockX;
            this.blockY = blockY;
            this.blockZ = blockZ;
            this.inTileId = inTileId;
            this.shakeTicks = shakeTicks;
            this.inGround = inGround;
        }

        public int getBlockX() {
            return blockX;
        }

        public int getBlockY() {
            return blockY;
        }

        public int getBlockZ() {
            return blockZ;
        }

        public int getInTileId() {
            return inTileId;
        }

        public int getShakeTicks() {
            return shakeTicks;
        }

        public boolean isInGround() {
            return inGround;
        }
    }
}
