package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LogLeafDecayBehaviour;

import java.util.Random;

public class BlockLog extends Block {
    private final LogLeafDecayBehaviour logLeafDecayService = LogLeafDecayBehaviour.getInstance();

    protected BlockLog(int i) {
        super(i, Material.WOOD);
        this.textureId = 20;
    }

    public int a(Random random) {
        return 1;
    }

    public int a(int i, Random random) {
        return Block.LOG.id;
    }

    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        super.a(world, entityhuman, i, j, k, l);
    }

    public void remove(World world, int i, int j, int k) {
        logLeafDecayService.markNearbyLeavesForDecay(world, i, j, k, Block.LEAVES.id);
    }

    public int a(int i, int j) {
        return logLeafDecayService.resolveTextureBySideAndVariant(i, j);
    }

    protected int a_(int i) {
        return i;
    }
}
