package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipesFood {

    public RecipesFood() {}

    public void a(CraftingManager craftingmanager) {
        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), CraftBlock.BROWN_MUSHROOM, Character.valueOf('Y'), CraftBlock.RED_MUSHROOM, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.MUSHROOM_SOUP), new Object[] { "Y", "X", "#", Character.valueOf('X'), CraftBlock.RED_MUSHROOM, Character.valueOf('Y'), CraftBlock.BROWN_MUSHROOM, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.BOWL});
        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(org.bukkit.craftbukkit.item.Item.INK_SACK, 1, 3), Character.valueOf('#'), Item.WHEAT});
    }

    public void addRecipes(CraftingManager craftingManager) {
        this.a(craftingManager);
    }

}
