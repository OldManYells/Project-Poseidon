package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MineralDropBehaviour;

import java.util.Random;

public class BlockClay extends Block {
    private final MineralDropBehaviour mineralDropService = MineralDropBehaviour.getInstance();

    public BlockClay(int i, int j) {
        super(i, j, Material.CLAY);
    }

    public int a(int i, Random random) {
        return mineralDropService.resolveClayDropItemId(Item.CLAY_BALL.id);
    }

    public int a(Random random) {
        return mineralDropService.resolveClayDropCount();
    }
}
