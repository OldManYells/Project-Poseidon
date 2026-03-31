package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipesArmor {

    private String[][] a = new String[][] { { "XXX", "X X"}, { "X X", "XXX", "XXX"}, { "XXX", "X X", "X X"}, { "X X", "X X"}};
    private Object[][] b;

    public RecipesArmor() {
        this.b = new Object[][] { { org.bukkit.craftbukkit.item.Item.LEATHER, CraftBlock.FIRE, org.bukkit.craftbukkit.item.Item.IRON_INGOT, org.bukkit.craftbukkit.item.Item.DIAMOND, org.bukkit.craftbukkit.item.Item.GOLD_INGOT}, { org.bukkit.craftbukkit.item.Item.LEATHER_HELMET, org.bukkit.craftbukkit.item.Item.CHAINMAIL_HELMET, org.bukkit.craftbukkit.item.Item.IRON_HELMET, org.bukkit.craftbukkit.item.Item.DIAMOND_HELMET, org.bukkit.craftbukkit.item.Item.GOLD_HELMET}, { org.bukkit.craftbukkit.item.Item.LEATHER_CHESTPLATE, org.bukkit.craftbukkit.item.Item.CHAINMAIL_CHESTPLATE, org.bukkit.craftbukkit.item.Item.IRON_CHESTPLATE, org.bukkit.craftbukkit.item.Item.DIAMOND_CHESTPLATE, org.bukkit.craftbukkit.item.Item.GOLD_CHESTPLATE}, { org.bukkit.craftbukkit.item.Item.LEATHER_LEGGINGS, org.bukkit.craftbukkit.item.Item.CHAINMAIL_LEGGINGS, org.bukkit.craftbukkit.item.Item.IRON_LEGGINGS, org.bukkit.craftbukkit.item.Item.DIAMOND_LEGGINGS, org.bukkit.craftbukkit.item.Item.GOLD_LEGGINGS}, { org.bukkit.craftbukkit.item.Item.LEATHER_BOOTS, org.bukkit.craftbukkit.item.Item.CHAINMAIL_BOOTS, org.bukkit.craftbukkit.item.Item.IRON_BOOTS, org.bukkit.craftbukkit.item.Item.DIAMOND_BOOTS, org.bukkit.craftbukkit.item.Item.GOLD_BOOTS}};
    }

    public void a(CraftingManager craftingmanager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            Object object = this.b[0][i];

            for (int j = 0; j < this.b.length - 1; ++j) {
                org.bukkit.craftbukkit.item.Item item = (Item) this.b[j + 1][i];

                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[j], Character.valueOf('X'), object});
            }
        }
    }

    public void addRecipes(CraftingManager craftingManager) {
        this.a(craftingManager);
    }

}
