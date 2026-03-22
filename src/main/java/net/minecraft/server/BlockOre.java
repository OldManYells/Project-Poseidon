package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MineralDropBehaviour;

import java.util.Random;

public class BlockOre extends Block {
    private final MineralDropBehaviour mineralDropService = MineralDropBehaviour.getInstance();

    public BlockOre(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return mineralDropService.resolveOreDropItemId(
                this.id,
                Block.COAL_ORE.id,
                Block.DIAMOND_ORE.id,
                Block.LAPIS_ORE.id,
                Item.COAL.id,
                Item.DIAMOND.id,
                Item.INK_SACK.id
        );
    }

    public int a(Random random) {
        return mineralDropService.resolveOreDropCount(this.id, Block.LAPIS_ORE.id, random);
    }

    protected int a_(int i) {
        return mineralDropService.resolveOreDropData(this.id, Block.LAPIS_ORE.id);
    }
}
