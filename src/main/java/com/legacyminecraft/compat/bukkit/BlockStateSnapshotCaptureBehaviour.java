package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for capturing block-state constructor snapshot data.
 */
public final class BlockStateSnapshotCaptureBehaviour {
    private static final BlockStateSnapshotCaptureBehaviour INSTANCE = new BlockStateSnapshotCaptureBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final ChunkWrapperProjectionBridgeBehaviour CHUNK_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            ChunkWrapperProjectionBridgeBehaviour.getInstance();

    private BlockStateSnapshotCaptureBehaviour() {
    }

    public static BlockStateSnapshotCaptureBehaviour getInstance() {
        return INSTANCE;
    }

    public SnapshotData capture(Object block) {
        return new SnapshotData(
                BridgeReflection.invoke(block, "getWorld"),
                BridgeReflection.invoke(block, "getChunk"),
                ((Number) BridgeReflection.invoke(block, "getX")).intValue(),
                ((Number) BridgeReflection.invoke(block, "getY")).intValue(),
                ((Number) BridgeReflection.invoke(block, "getZ")).intValue(),
                ((Number) BridgeReflection.invoke(block, "getTypeId")).intValue(),
                ((Number) BridgeReflection.invoke(block, "getLightLevel")).byteValue(),
                ((Number) BridgeReflection.invoke(block, "getData")).byteValue()
        );
    }

    public static final class SnapshotData {
        private final Object world;
        private final Object chunk;
        private final int x;
        private final int y;
        private final int z;
        private final int typeId;
        private final byte lightLevel;
        private final byte data;

        public SnapshotData(
                Object world,
                Object chunk,
                int x,
                int y,
                int z,
                int typeId,
                byte lightLevel,
                byte data
        ) {
            this.world = world;
            this.chunk = chunk;
            this.x = x;
            this.y = y;
            this.z = z;
            this.typeId = typeId;
            this.lightLevel = lightLevel;
            this.data = data;
        }

        public <T> T getWorld() {
            return BridgeReflection.cast(world);
        }

        public <T> T getChunk() {
            return BridgeReflection.cast(chunk);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getTypeId() {
            return typeId;
        }

        public byte getLightLevel() {
            return lightLevel;
        }

        public byte getData() {
            return data;
        }
    }
}
