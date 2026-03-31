package net.minecraft.server;


import org.bukkit.craftbukkit.block.BlockCloth;
import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipesDyes {

    public RecipesDyes() {}

    public void a(CraftingManager craftingmanager) {
        for (int i = 0; i < 16; ++i) {
            craftingmanager.registerShapelessRecipe(new ItemStack(CraftBlock.WOOL, 1, BlockCloth.d(i)), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, i), new ItemStack(org.bukkit.craftbukkit.item.Item.byId[CraftBlock.WOOL.id], 1, 0)});
        }

        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 11), new Object[] { CraftBlock.YELLOW_FLOWER});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 1), new Object[] { CraftBlock.RED_ROSE});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 3, 15), new Object[] { org.bukkit.craftbukkit.item.Item.BONE});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 9), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 14), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 11)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 10), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 2), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 8), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 0), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 7), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 8), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 3, 7), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 0), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 12), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 4), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 15)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 6), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 4), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 2)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 5), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 4), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 2, 13), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 5), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 9)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 3, 13), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 4), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 9)});
        craftingmanager.registerShapelessRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 4, 13), new Object[] { new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 4), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 15)});
    }

    public void addRecipes(CraftingManager craftingManager) {
        this.a(craftingManager);
    }

}
