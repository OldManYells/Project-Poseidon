package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation flower block scaffold.
 */
public class BlockFlower extends Block {
    public BlockFlower(int id, Material material) {
        super(id, material);
    }

    public boolean f(World world, int x, int y, int z) {
        return true;
    }
}
