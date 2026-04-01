package com.legacyminecraft.poseidon.world.item;

import com.legacyminecraft.poseidon.world.block.Block;

public class ItemAxe extends ItemTool {

    private static Block[] bk = new Block[] { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST};

    public ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, bk);
    }
}
