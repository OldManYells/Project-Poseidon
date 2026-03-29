package net.minecraft.server;


public class ItemPickaxe extends ItemTool {

    private static Block[] bk = new Block[] { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK};

    protected ItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, bk);
    }

    public boolean a(Block baseBlock) {
        return baseBlock == Block.OBSIDIAN ? this.a.d() == 3 : (baseBlock != Block.DIAMOND_BLOCK && baseBlock != Block.DIAMOND_ORE ? (baseBlock != Block.GOLD_BLOCK && baseBlock != Block.GOLD_ORE ? (baseBlock != Block.IRON_BLOCK && baseBlock != Block.IRON_ORE ? (baseBlock != Block.LAPIS_BLOCK && baseBlock != Block.LAPIS_ORE ? (baseBlock != Block.REDSTONE_ORE && baseBlock != Block.GLOWING_REDSTONE_ORE ? (baseBlock.material == Material.STONE ? true : baseBlock.material == Material.ORE) : this.a.d() >= 2) : this.a.d() >= 1) : this.a.d() >= 1) : this.a.d() >= 2) : this.a.d() >= 2);
    }
}
