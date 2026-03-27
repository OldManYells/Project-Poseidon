package com.legacyminecraft.poseidon.world;

/**
 * Minimal plant block for world generation placement checks.
 */
public class BlockFlower extends Block {
    public BlockFlower(int id, Material material, String name) {
        super(id, material, name);
    }

    @Override
    public boolean f(World world, int x, int y, int z) {
        return world != null;
    }
}
