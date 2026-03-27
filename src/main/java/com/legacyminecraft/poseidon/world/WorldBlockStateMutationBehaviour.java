package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for world block state mutation orchestration.
 */
public final class WorldBlockStateMutationBehaviour {
    private static final WorldBlockStateMutationBehaviour INSTANCE = new WorldBlockStateMutationBehaviour();

    private WorldBlockStateMutationBehaviour() {
    }

    public static WorldBlockStateMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public DataMutationResult applyRawData(World world, int x, int y, int z, int data) {
        if (!world.setRawData(x, y, z, data)) {
            return DataMutationResult.notChanged();
        }

        int typeId = world.getTypeId(x, y, z);
        boolean shouldNotifyUpdate = Block.t[typeId & 255];
        return DataMutationResult.changed(typeId, shouldNotifyUpdate);
    }

    public boolean applyRawTypeId(World world, int x, int y, int z, int typeId) {
        return world.setRawTypeId(x, y, z, typeId);
    }

    public boolean applyRawTypeIdAndData(World world, int x, int y, int z, int typeId, int data) {
        return world.setRawTypeIdAndData(x, y, z, typeId, data);
    }

    public int resolveUpdateTypeId(int oldTypeId, int newTypeId) {
        return newTypeId == 0 ? oldTypeId : newTypeId;
    }

    public static final class DataMutationResult {
        public final boolean changed;
        public final int typeId;
        public final boolean shouldNotifyUpdate;

        private DataMutationResult(boolean changed, int typeId, boolean shouldNotifyUpdate) {
            this.changed = changed;
            this.typeId = typeId;
            this.shouldNotifyUpdate = shouldNotifyUpdate;
        }

        public static DataMutationResult notChanged() {
            return new DataMutationResult(false, 0, false);
        }

        public static DataMutationResult changed(int typeId, boolean shouldNotifyUpdate) {
            return new DataMutationResult(true, typeId, shouldNotifyUpdate);
        }
    }
}
