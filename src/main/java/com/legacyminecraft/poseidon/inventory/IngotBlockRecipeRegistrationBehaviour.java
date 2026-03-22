package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Block;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class IngotBlockRecipeRegistrationBehaviour {
    private static final IngotBlockRecipeRegistrationBehaviour INSTANCE = new IngotBlockRecipeRegistrationBehaviour();

    private IngotBlockRecipeRegistrationBehaviour() {
    }

    public static IngotBlockRecipeRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public Object[][] createRecipePairs() {
        return new Object[][] {
                {Block.GOLD_BLOCK, new ItemStack(Item.GOLD_INGOT, 9)},
                {Block.IRON_BLOCK, new ItemStack(Item.IRON_INGOT, 9)},
                {Block.DIAMOND_BLOCK, new ItemStack(Item.DIAMOND, 9)},
                {Block.LAPIS_BLOCK, new ItemStack(Item.INK_SACK, 9, 4)}
        };
    }

    public void registerAll(CraftingManager craftingmanager, Object[][] recipePairs) {
        for (int i = 0; i < recipePairs.length; ++i) {
            Block block = (Block) recipePairs[i][0];
            ItemStack itemstack = (ItemStack) recipePairs[i][1];

            craftingmanager.registerShapedRecipe(new ItemStack(block), new Object[]{"###", "###", "###", Character.valueOf('#'), itemstack});
            craftingmanager.registerShapedRecipe(itemstack, new Object[]{"#", Character.valueOf('#'), block});
        }
    }
}
