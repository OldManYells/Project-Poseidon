package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical behaviour for finalizing piston moving-tile replacement in world state.
 */
public final class PistonTileFinalizationBehaviour {
    private static final PistonTileFinalizationBehaviour INSTANCE = new PistonTileFinalizationBehaviour();

    private PistonTileFinalizationBehaviour() {
    }

    public static PistonTileFinalizationBehaviour getInstance() {
        return INSTANCE;
    }

    public void finalizeMovingTile(World world,
                                   int x,
                                   int y,
                                   int z,
                                   int movedBlockId,
                                   int movedBlockData,
                                   Runnable clearTileEntityAction) {
        world.o(x, y, z);
        clearTileEntityAction.run();
        if (world.getTypeId(x, y, z) == Block.PISTON_MOVING.id) {
            world.setTypeIdAndData(x, y, z, movedBlockId, movedBlockData);
        }
    }
}
