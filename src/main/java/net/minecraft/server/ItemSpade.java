package net.minecraft.server;

import com.legacyminecraft.poseidon.item.SpadeHarvestBehaviour;

public class ItemSpade extends ItemTool {
    private static final SpadeHarvestBehaviour SPADE_HARVEST_BEHAVIOUR = SpadeHarvestBehaviour.getInstance();

    private static Block[] bk = new Block[] { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL};

    public ItemSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, bk);
    }

    public boolean a(Block block) {
        return SPADE_HARVEST_BEHAVIOUR.canHarvest(block);
    }
}
