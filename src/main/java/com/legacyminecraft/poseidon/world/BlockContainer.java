package com.legacyminecraft.poseidon.world;

/**
 * Minimal tile-entity-capable block.
 */
public class BlockContainer extends Block {
    public BlockContainer(int id, Material material, String name) {
        super(id, material, name);
        isTileEntity[id] = true;
    }

    @Override
    public void c(World world, int x, int y, int z) {
    }
}
