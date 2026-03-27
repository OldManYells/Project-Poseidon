package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat block-state scaffold.
 */
public class BlockState {
    private final Block block;
    private int typeId;
    private int rawData;

    public BlockState(Block block) {
        this.block = block;
        this.typeId = block == null ? 0 : block.getTypeId();
    }

    public Block getBlock() {
        return block;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRawData() {
        return rawData;
    }

    public void setRawData(int rawData) {
        this.rawData = rawData;
    }
}
