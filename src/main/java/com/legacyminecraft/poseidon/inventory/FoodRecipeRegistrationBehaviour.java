package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Block;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class FoodRecipeRegistrationBehaviour {
    private static final FoodRecipeRegistrationBehaviour INSTANCE = new FoodRecipeRegistrationBehaviour();

    private FoodRecipeRegistrationBehaviour() {
    }

    public static FoodRecipeRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerAll(CraftingManager craftingmanager) {
        craftingmanager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] {"Y", "X", "#", Character.valueOf('X'), Block.BROWN_MUSHROOM, Character.valueOf('Y'), Block.RED_MUSHROOM, Character.valueOf('#'), Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] {"Y", "X", "#", Character.valueOf('X'), Block.RED_MUSHROOM, Character.valueOf('Y'), Block.BROWN_MUSHROOM, Character.valueOf('#'), Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), new Object[] {"#X#", Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 3), Character.valueOf('#'), Item.WHEAT});
    }
}
