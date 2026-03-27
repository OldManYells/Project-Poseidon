package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state raw data access and mutation.
 */
public final class BlockStateRawDataBehaviour {
    private static final BlockStateRawDataBehaviour INSTANCE = new BlockStateRawDataBehaviour();

    private BlockStateRawDataBehaviour() {
    }

    public static BlockStateRawDataBehaviour getInstance() {
        return INSTANCE;
    }

    public byte getRawData(org.bukkit.material.MaterialData materialData) {
        return (byte) materialData.getData();
    }

    public org.bukkit.material.MaterialData setRawData(int typeId, byte data, BlockStateDataBehaviour dataBehaviour) {
        return dataBehaviour.createData(typeId, data);
    }
}
