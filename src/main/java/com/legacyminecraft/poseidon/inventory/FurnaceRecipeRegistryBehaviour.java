package com.legacyminecraft.poseidon.inventory;


import java.util.Map;

public final class FurnaceRecipeRegistryBehaviour {
    private static final FurnaceRecipeRegistryBehaviour INSTANCE = new FurnaceRecipeRegistryBehaviour();

    private FurnaceRecipeRegistryBehaviour() {
    }

    public static FurnaceRecipeRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerDefaultRecipes(Map recipes) {
        this.registerRecipe(recipes, Block.IRON_ORE.id, new ItemStack(Item.IRON_INGOT));
        this.registerRecipe(recipes, Block.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT));
        this.registerRecipe(recipes, Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND));
        this.registerRecipe(recipes, Block.SAND.id, new ItemStack(Block.GLASS));
        this.registerRecipe(recipes, Item.PORK.id, new ItemStack(Item.GRILLED_PORK));
        this.registerRecipe(recipes, Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH));
        this.registerRecipe(recipes, Block.COBBLESTONE.id, new ItemStack(Block.STONE));
        this.registerRecipe(recipes, Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK));
        this.registerRecipe(recipes, Block.CACTUS.id, new ItemStack(Item.INK_SACK, 1, 2));
        this.registerRecipe(recipes, Block.LOG.id, new ItemStack(Item.COAL, 1, 1));
    }

    public void registerRecipe(Map recipes, int sourceId, ItemStack result) {
        recipes.put(Integer.valueOf(sourceId), result);
    }

    public ItemStack getRecipe(Map recipes, int sourceId) {
        return (ItemStack) recipes.get(Integer.valueOf(sourceId));
    }

    public Map getRecipes(Map recipes) {
        return recipes;
    }
}
