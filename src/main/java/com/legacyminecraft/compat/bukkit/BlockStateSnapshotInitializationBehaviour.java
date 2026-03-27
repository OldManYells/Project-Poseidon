package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBlockState snapshot initialization.
 */
public final class BlockStateSnapshotInitializationBehaviour {
    private static final BlockStateSnapshotInitializationBehaviour INSTANCE =
            new BlockStateSnapshotInitializationBehaviour();
    private static final BlockStateSnapshotCaptureBehaviour SNAPSHOT_CAPTURE_BEHAVIOUR =
            BlockStateSnapshotCaptureBehaviour.getInstance();
    private static final BlockStateDataBehaviour DATA_BEHAVIOUR =
            BlockStateDataBehaviour.getInstance();

    private BlockStateSnapshotInitializationBehaviour() {
    }

    public static BlockStateSnapshotInitializationBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T initialize(Object block) {
        BlockStateSnapshotCaptureBehaviour.SnapshotData snapshotData = SNAPSHOT_CAPTURE_BEHAVIOUR.capture(block);
        return BridgeReflection.cast(new SnapshotState(
                snapshotData.getWorld(),
                snapshotData.getChunk(),
                snapshotData.getX(),
                snapshotData.getY(),
                snapshotData.getZ(),
                snapshotData.getTypeId(),
                snapshotData.getLightLevel(),
                DATA_BEHAVIOUR.createData(snapshotData.getTypeId(), snapshotData.getData())
        ));
    }

    public static final class SnapshotState {
        private final Object world;
        private final Object chunk;
        private final int x;
        private final int y;
        private final int z;
        private final int typeId;
        private final byte lightLevel;
        private final Object data;

        public SnapshotState(
                Object world,
                Object chunk,
                int x,
                int y,
                int z,
                int typeId,
                byte lightLevel,
                Object data
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

        public <T> T getData() {
            return BridgeReflection.cast(data);
        }
    }
}
