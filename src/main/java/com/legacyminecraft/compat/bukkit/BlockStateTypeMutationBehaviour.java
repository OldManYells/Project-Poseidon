package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state type mutation and default data reset.
 */
public final class BlockStateTypeMutationBehaviour {
    private static final BlockStateTypeMutationBehaviour INSTANCE = new BlockStateTypeMutationBehaviour();

    private BlockStateTypeMutationBehaviour() {
    }

    public static BlockStateTypeMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T setType(Object type, BlockStateDataBehaviour dataBehaviour) {
        return BridgeReflection.cast(setTypeId(((Number) BridgeReflection.invoke(type, "getId")).intValue(), dataBehaviour));
    }

    public <T> T setTypeId(int typeId, BlockStateDataBehaviour dataBehaviour) {
        Object resetData = dataBehaviour.createData(typeId, (byte) 0);
        return BridgeReflection.cast(new MutationResult(typeId, resetData));
    }

    public static final class MutationResult {
        private final int typeId;
        private final Object data;

        public MutationResult(int typeId, Object data) {
            this.typeId = typeId;
            this.data = data;
        }

        public int getTypeId() {
            return typeId;
        }

        public <T> T getData() {
            return BridgeReflection.cast(data);
        }
    }
}
