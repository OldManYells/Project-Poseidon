package com.legacyminecraft.poseidon.item;

/**
 * Item-local minecart-track block scaffold.
 */
public class BlockMinecartTrack extends Block {
    public BlockMinecartTrack(int id, Material material) {
        super(id, material);
    }

    public static boolean g(World world, int x, int y, int z) {
        return true;
    }
}
