package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipeIngots {

    private Object[][] a;

    public RecipeIngots() {
        this.a = new Object[][] { { CraftBlock.GOLD_BLOCK, new ItemStack(org.bukkit.craftbukkit.item.Item.GOLD_INGOT, 9)}, { CraftBlock.IRON_BLOCK, new ItemStack(org.bukkit.craftbukkit.item.Item.IRON_INGOT, 9)}, { CraftBlock.DIAMOND_BLOCK, new ItemStack(org.bukkit.craftbukkit.item.Item.DIAMOND, 9)}, { CraftBlock.LAPIS_BLOCK, new ItemStack(Item.INK_SACK, 9, 4)}};
    }

    public void a(CraftingManager craftingmanager) {
        for (int i = 0; i < this.a.length; ++i) {
            CraftBlock baseBlock = (CraftBlock) this.a[i][0];
            ItemStack itemstack = (ItemStack) this.a[i][1];

            craftingmanager.registerShapedRecipe(new ItemStack(baseBlock), new Object[] { "###", "###", "###", Character.valueOf('#'), itemstack});
            craftingmanager.registerShapedRecipe(itemstack, new Object[] { "#", Character.valueOf('#'), baseBlock});
        }
    }

    public void addRecipes(CraftingManager craftingManager) {
        this.a(craftingManager);
    }

}
