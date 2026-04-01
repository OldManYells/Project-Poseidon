package com.legacyminecraft.poseidon.world.inventory;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.item.*;

public class RecipesFood {

    public RecipesFood() {}

    public void a(CraftingManager craftingmanager) {
        craftingmanager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), Block.BROWN_MUSHROOM, Character.valueOf('Y'), Block.RED_MUSHROOM, Character.valueOf('#'), Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), Block.RED_MUSHROOM, Character.valueOf('Y'), Block.BROWN_MUSHROOM, Character.valueOf('#'), Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 3), Character.valueOf('#'), Item.WHEAT});
    }
}
