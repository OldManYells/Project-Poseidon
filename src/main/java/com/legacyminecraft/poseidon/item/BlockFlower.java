package com.legacyminecraft.poseidon.item;

/**
 * Item-local flower block scaffold.
 */
public class BlockFlower extends Block {
    public BlockFlower(int id, Material material) {
        super(id, material);
    }

    public boolean f(World world, int x, int y, int z) {
        return true;
    }
}
