package net.minecraft.server;


public class ItemSpade extends ItemTool {

    private static CraftBlock[] bk = new CraftBlock[] { CraftBlock.GRASS, CraftBlock.DIRT, CraftBlock.SAND, CraftBlock.GRAVEL, CraftBlock.SNOW, CraftBlock.SNOW_BLOCK, CraftBlock.CLAY, CraftBlock.SOIL};

    public ItemSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, bk);
    }

    public boolean a(CraftBlock baseBlock) {
        return baseBlock == CraftBlock.SNOW ? true : baseBlock == CraftBlock.SNOW_BLOCK;
    }
}
