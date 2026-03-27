package com.legacyminecraft.poseidon.block;

/**
 * Minimal moving-piston tile-entity factory used by piston chain behaviour.
 */
public final class BlockPistonMoving {
    private BlockPistonMoving() {
    }

    public static TileEntityPiston a(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead) {
        return new TileEntityPiston(movedBlockId, movedBlockData, facing, extending, renderHead);
    }
}
