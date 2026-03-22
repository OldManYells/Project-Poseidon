package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MineralDropBehaviour;

import java.util.Random;

public class BlockLightStone extends Block {
    private final MineralDropBehaviour mineralDropService = MineralDropBehaviour.getInstance();

    public BlockLightStone(int i, int j, Material material) {
        super(i, j, material);
    }

    public int a(Random random) {
        return mineralDropService.resolveGlowstoneDustDropCount(random);
    }

    public int a(int i, Random random) {
        return mineralDropService.resolveGlowstoneDustDropItemId(Item.GLOWSTONE_DUST.id);
    }
}
