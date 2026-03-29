package org.bukkit.craftbukkit.item;


import net.minecraft.server.CraftBlock;
import net.minecraft.server.EnumToolMaterial;

public class ItemAxe extends ItemTool {

    private static CraftBlock[] bk = new CraftBlock[] { CraftBlock.WOOD, CraftBlock.BOOKSHELF, CraftBlock.LOG, CraftBlock.CHEST};

    protected ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, bk);
    }
}
