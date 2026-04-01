package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.item.EnumToolMaterial;
import com.legacyminecraft.poseidon.world.block.Block;
import com.legacyminecraft.poseidon.*;

public class ItemAxe extends ItemTool {

    private static Block[] bk = new Block[] { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST};

    public ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, bk);
    }
}
