package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

public class RecipesTools {

    private String[][] a = new String[][] { { "XXX", " # ", " # "}, { "X", "#", "#"}, { "XX", "X#", " #"}, { "XX", " #", " #"}};
    private Object[][] b;

    public RecipesTools() {
        this.b = new Object[][] { { CraftBlock.WOOD, CraftBlock.COBBLESTONE, org.bukkit.craftbukkit.item.Item.IRON_INGOT, org.bukkit.craftbukkit.item.Item.DIAMOND, org.bukkit.craftbukkit.item.Item.GOLD_INGOT}, { org.bukkit.craftbukkit.item.Item.WOOD_PICKAXE, org.bukkit.craftbukkit.item.Item.STONE_PICKAXE, org.bukkit.craftbukkit.item.Item.IRON_PICKAXE, org.bukkit.craftbukkit.item.Item.DIAMOND_PICKAXE, org.bukkit.craftbukkit.item.Item.GOLD_PICKAXE}, { org.bukkit.craftbukkit.item.Item.WOOD_SPADE, org.bukkit.craftbukkit.item.Item.STONE_SPADE, org.bukkit.craftbukkit.item.Item.IRON_SPADE, org.bukkit.craftbukkit.item.Item.DIAMOND_SPADE, org.bukkit.craftbukkit.item.Item.GOLD_SPADE}, { org.bukkit.craftbukkit.item.Item.WOOD_AXE, org.bukkit.craftbukkit.item.Item.STONE_AXE, org.bukkit.craftbukkit.item.Item.IRON_AXE, org.bukkit.craftbukkit.item.Item.DIAMOND_AXE, org.bukkit.craftbukkit.item.Item.GOLD_AXE}, { org.bukkit.craftbukkit.item.Item.WOOD_HOE, org.bukkit.craftbukkit.item.Item.STONE_HOE, org.bukkit.craftbukkit.item.Item.IRON_HOE, org.bukkit.craftbukkit.item.Item.DIAMOND_HOE, org.bukkit.craftbukkit.item.Item.GOLD_HOE}};
    }

    public void a(CraftingManager craftingmanager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            Object object = this.b[0][i];

            for (int j = 0; j < this.b.length - 1; ++j) {
                org.bukkit.craftbukkit.item.Item item = (org.bukkit.craftbukkit.item.Item) this.b[j + 1][i];

                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[j], Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK, Character.valueOf('X'), object});
            }
        }

        craftingmanager.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.SHEARS), new Object[] { " #", "# ", Character.valueOf('#'), Item.IRON_INGOT});
    }
}
