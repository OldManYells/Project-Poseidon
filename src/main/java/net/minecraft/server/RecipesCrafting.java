package net.minecraft.server;


public class RecipesCrafting {

    public RecipesCrafting() {}

    public void a(CraftingManager craftingmanager) {
        craftingmanager.registerShapedRecipe(new ItemStack(CraftBlock.CHEST), new Object[] { "###", "# #", "###", Character.valueOf('#'), CraftBlock.WOOD});
        craftingmanager.registerShapedRecipe(new ItemStack(CraftBlock.FURNACE), new Object[] { "###", "# #", "###", Character.valueOf('#'), CraftBlock.COBBLESTONE});
        craftingmanager.registerShapedRecipe(new ItemStack(CraftBlock.WORKBENCH), new Object[] { "##", "##", Character.valueOf('#'), CraftBlock.WOOD});
        craftingmanager.registerShapedRecipe(new ItemStack(CraftBlock.SANDSTONE), new Object[] { "##", "##", Character.valueOf('#'), CraftBlock.SAND});
    }
}
