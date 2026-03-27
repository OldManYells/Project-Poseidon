package com.legacyminecraft.poseidon.inventory;


public final class CraftingBlockRecipeRegistrationBehaviour {
    private static final CraftingBlockRecipeRegistrationBehaviour INSTANCE = new CraftingBlockRecipeRegistrationBehaviour();

    private CraftingBlockRecipeRegistrationBehaviour() {
    }

    public static CraftingBlockRecipeRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerAll(CraftingManager craftingmanager) {
        craftingmanager.registerShapedRecipe(new ItemStack(Block.CHEST), new Object[] {"###", "# #", "###", Character.valueOf('#'), Block.WOOD});
        craftingmanager.registerShapedRecipe(new ItemStack(Block.FURNACE), new Object[] {"###", "# #", "###", Character.valueOf('#'), Block.COBBLESTONE});
        craftingmanager.registerShapedRecipe(new ItemStack(Block.WORKBENCH), new Object[] {"##", "##", Character.valueOf('#'), Block.WOOD});
        craftingmanager.registerShapedRecipe(new ItemStack(Block.SANDSTONE), new Object[] {"##", "##", Character.valueOf('#'), Block.SAND});
    }
}
