package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat block-fade event scaffold.
 */
public class BlockFadeEvent extends Event {
    private final Block block;
    private final BlockState newState;

    public BlockFadeEvent(Block block, BlockState newState) {
        this.block = block;
        this.newState = newState;
    }

    public Block getBlock() {
        return block;
    }

    public BlockState getNewState() {
        return newState;
    }
}
