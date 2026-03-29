package net.minecraft.server;


public class ItemAxe extends ItemTool {

    private static CraftBlock[] bk = new CraftBlock[] { CraftBlock.WOOD, CraftBlock.BOOKSHELF, CraftBlock.LOG, CraftBlock.CHEST};

    protected ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, bk);
    }
}
