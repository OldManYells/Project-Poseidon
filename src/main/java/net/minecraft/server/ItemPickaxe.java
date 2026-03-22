package net.minecraft.server;

import com.legacyminecraft.poseidon.item.PickaxeHarvestBehaviour;

public class ItemPickaxe extends ItemTool {
    private static final PickaxeHarvestBehaviour PICKAXE_HARVEST_BEHAVIOUR = PickaxeHarvestBehaviour.getInstance();

    private static Block[] bk = new Block[] { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK};

    protected ItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, bk);
    }

    public boolean a(Block block) {
        return PICKAXE_HARVEST_BEHAVIOUR.canHarvest(block, this.a);
    }
}
