package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipesWeapons {

    private String[][] a = new String[][] { { "X", "X", "#"}};
    private Object[][] b;

    public RecipesWeapons() {
        this.b = new Object[][] { { CraftBlock.WOOD, CraftBlock.COBBLESTONE, org.bukkit.craftbukkit.item.Item.IRON_INGOT, org.bukkit.craftbukkit.item.Item.DIAMOND, org.bukkit.craftbukkit.item.Item.GOLD_INGOT}, { org.bukkit.craftbukkit.item.Item.WOOD_SWORD, org.bukkit.craftbukkit.item.Item.STONE_SWORD, org.bukkit.craftbukkit.item.Item.IRON_SWORD, org.bukkit.craftbukkit.item.Item.DIAMOND_SWORD, org.bukkit.craftbukkit.item.Item.GOLD_SWORD}};
    }

    public void a(CraftingManager craftingmanager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            Object object = this.b[0][i];

            for (int j = 0; j < this.b.length - 1; ++j) {
                org.bukkit.craftbukkit.item.Item item = (org.bukkit.craftbukkit.item.Item) this.b[j + 1][i];

                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[j], Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK, Character.valueOf('X'), object});
            }
        }

        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BOW, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.STRING, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), org.bukkit.craftbukkit.item.Item.FEATHER, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.FLINT, Character.valueOf('#'), Item.STICK});
    }
}
