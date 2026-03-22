package net.minecraft.server;

import com.legacyminecraft.poseidon.block.GravelDropBehaviour;

import java.util.Random;

public class BlockGravel extends BlockSand {
    private final GravelDropBehaviour gravelDropService = GravelDropBehaviour.getInstance();

    public BlockGravel(int i, int j) {
        super(i, j);
    }

    public int a(int i, Random random) {
        return gravelDropService.resolveDropItemId(random, this.id, Item.FLINT.id, gravelDropService.flintChanceDivisor());
    }
}
