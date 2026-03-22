package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Block;
import net.minecraft.server.World;

/**
 * Canonical behaviour for explosion-driven block destruction/drop flow.
 */
public final class ExplosionBlockDestructionBehaviour {
    private static final ExplosionBlockDestructionBehaviour INSTANCE = new ExplosionBlockDestructionBehaviour();

    private ExplosionBlockDestructionBehaviour() {
    }

    public static ExplosionBlockDestructionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldDestroyBlock(int blockTypeId) {
        return blockTypeId > 0 && blockTypeId != Block.FIRE.id;
    }

    public void destroyAndDrop(World world, int x, int y, int z, int blockTypeId, float yield) {
        Block.byId[blockTypeId].dropNaturally(world, x, y, z, world.getData(x, y, z), yield);
        world.setTypeId(x, y, z, 0);
        Block.byId[blockTypeId].d(world, x, y, z);
    }
}
