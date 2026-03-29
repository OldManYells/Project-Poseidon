package net.minecraft.server;


import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes {

    private static final FurnaceRecipes a = new FurnaceRecipes();
    private Map b = new HashMap();

    public static final FurnaceRecipes getInstance() {
        return a;
    }

    private FurnaceRecipes() {
        this.registerRecipe(CraftBlock.IRON_ORE.id, new ItemStack(Item.IRON_INGOT));
        this.registerRecipe(CraftBlock.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT));
        this.registerRecipe(CraftBlock.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND));
        this.registerRecipe(CraftBlock.SAND.id, new ItemStack(CraftBlock.GLASS));
        this.registerRecipe(Item.PORK.id, new ItemStack(Item.GRILLED_PORK));
        this.registerRecipe(Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH));
        this.registerRecipe(CraftBlock.COBBLESTONE.id, new ItemStack(CraftBlock.STONE));
        this.registerRecipe(Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK));
        this.registerRecipe(CraftBlock.CACTUS.id, new ItemStack(Item.INK_SACK, 1, 2));
        this.registerRecipe(CraftBlock.LOG.id, new ItemStack(Item.COAL, 1, 1));
    }

    public void registerRecipe(int i, ItemStack itemstack) {
        this.b.put(Integer.valueOf(i), itemstack);
    }

    public ItemStack a(int i) {
        return (ItemStack) this.b.get(Integer.valueOf(i));
    }

    public Map b() {
        return this.b;
    }
}
