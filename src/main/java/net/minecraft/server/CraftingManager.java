package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CraftingManager {

    private static final CraftingManager a = new CraftingManager();
    private List b = new ArrayList();

    public static final CraftingManager getInstance() {
        return a;
    }

    private CraftingManager() {
        (new RecipesTools()).a(this);
        (new RecipesWeapons()).a(this);
        (new RecipeIngots()).a(this);
        (new RecipesFood()).a(this);
        (new RecipesCrafting()).a(this);
        (new RecipesArmor()).a(this);
        (new RecipesDyes()).a(this);
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.PAPER, 3), new Object[] { "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.SUGAR_CANE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BOOK, 1), new Object[] { "#", "#", "#", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.PAPER});
        this.registerShapedRecipe(new ItemStack(CraftBlock.FENCE, 2), new Object[] { "###", "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.JUKEBOX, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), CraftBlock.WOOD, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.DIAMOND});
        this.registerShapedRecipe(new ItemStack(CraftBlock.NOTE_BLOCK, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), CraftBlock.WOOD, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.BOOKSHELF, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), CraftBlock.WOOD, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.BOOK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.SNOW_BLOCK, 1), new Object[] { "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.SNOW_BALL});
        this.registerShapedRecipe(new ItemStack(CraftBlock.CLAY, 1), new Object[] { "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.CLAY_BALL});
        this.registerShapedRecipe(new ItemStack(CraftBlock.BRICK, 1), new Object[] { "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.CLAY_BRICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.GLOWSTONE, 1), new Object[] { "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.GLOWSTONE_DUST});
        this.registerShapedRecipe(new ItemStack(CraftBlock.WOOL, 1), new Object[] { "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STRING});
        this.registerShapedRecipe(new ItemStack(CraftBlock.TNT, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.SULPHUR, Character.valueOf('#'), CraftBlock.SAND});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STEP, 3, 3), new Object[] { "###", Character.valueOf('#'), CraftBlock.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STEP, 3, 0), new Object[] { "###", Character.valueOf('#'), CraftBlock.STONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STEP, 3, 1), new Object[] { "###", Character.valueOf('#'), CraftBlock.SANDSTONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STEP, 3, 2), new Object[] { "###", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.LADDER, 2), new Object[] { "# #", "###", "# #", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.WOOD_DOOR, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.TRAP_DOOR, 2), new Object[] { "###", "###", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.IRON_DOOR, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.SIGN, 1), new Object[] { "###", "###", " X ", Character.valueOf('#'), CraftBlock.WOOD, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.CAKE, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), org.bukkit.craftbukkit.item.Item.MILK_BUCKET, Character.valueOf('B'), org.bukkit.craftbukkit.item.Item.SUGAR, Character.valueOf('C'), org.bukkit.craftbukkit.item.Item.WHEAT, Character.valueOf('E'), org.bukkit.craftbukkit.item.Item.EGG});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.SUGAR, 1), new Object[] { "#", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.SUGAR_CANE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.WOOD, 4), new Object[] { "#", Character.valueOf('#'), CraftBlock.LOG});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.STICK, 4), new Object[] { "#", "#", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.COAL, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(org.bukkit.craftbukkit.item.Item.COAL, 1, 1), Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BOWL, 4), new Object[] { "# #", " # ", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.RAILS, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.IRON_INGOT, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.GOLDEN_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.GOLD_INGOT, Character.valueOf('R'), org.bukkit.craftbukkit.item.Item.REDSTONE, Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.DETECTOR_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.IRON_INGOT, Character.valueOf('R'), org.bukkit.craftbukkit.item.Item.REDSTONE, Character.valueOf('#'), CraftBlock.STONE_PLATE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.MINECART, 1), new Object[] { "# #", "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(CraftBlock.JACK_O_LANTERN, 1), new Object[] { "A", "B", Character.valueOf('A'), CraftBlock.PUMPKIN, Character.valueOf('B'), CraftBlock.TORCH});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.STORAGE_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), CraftBlock.CHEST, Character.valueOf('B'), org.bukkit.craftbukkit.item.Item.MINECART});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.POWERED_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), CraftBlock.FURNACE, Character.valueOf('B'), org.bukkit.craftbukkit.item.Item.MINECART});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BOAT, 1), new Object[] { "# #", "###", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BUCKET, 1), new Object[] { "# #", " # ", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.FLINT_AND_STEEL, 1), new Object[] { "A ", " B", Character.valueOf('A'), org.bukkit.craftbukkit.item.Item.IRON_INGOT, Character.valueOf('B'), org.bukkit.craftbukkit.item.Item.FLINT});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BREAD, 1), new Object[] { "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.WHEAT});
        this.registerShapedRecipe(new ItemStack(CraftBlock.WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.FISHING_ROD, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.STRING});
        this.registerShapedRecipe(new ItemStack(CraftBlock.COBBLESTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), CraftBlock.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.PAINTING, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK, Character.valueOf('X'), CraftBlock.WOOL});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.GOLDEN_APPLE, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), CraftBlock.GOLD_BLOCK, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.APPLE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.LEVER, 1), new Object[] { "X", "#", Character.valueOf('#'), CraftBlock.COBBLESTONE, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.STICK});
        this.registerShapedRecipe(new ItemStack(CraftBlock.REDSTONE_TORCH_ON, 1), new Object[] { "X", "#", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.STICK, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.DIODE, 1), new Object[] { "#X#", "III", Character.valueOf('#'), CraftBlock.REDSTONE_TORCH_ON, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.REDSTONE, Character.valueOf('I'), CraftBlock.STONE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.WATCH, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.GOLD_INGOT, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.COMPASS, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.IRON_INGOT, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.MAP, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), org.bukkit.craftbukkit.item.Item.PAPER, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.COMPASS});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STONE_BUTTON, 1), new Object[] { "#", "#", Character.valueOf('#'), CraftBlock.STONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.STONE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), CraftBlock.STONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.WOOD_PLATE, 1), new Object[] { "##", Character.valueOf('#'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.DISPENSER, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), CraftBlock.COBBLESTONE, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.BOW, Character.valueOf('R'), org.bukkit.craftbukkit.item.Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(CraftBlock.PISTON, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), CraftBlock.COBBLESTONE, Character.valueOf('X'), org.bukkit.craftbukkit.item.Item.IRON_INGOT, Character.valueOf('R'), org.bukkit.craftbukkit.item.Item.REDSTONE, Character.valueOf('T'), CraftBlock.WOOD});
        this.registerShapedRecipe(new ItemStack(CraftBlock.PISTON_STICKY, 1), new Object[] { "S", "P", Character.valueOf('S'), org.bukkit.craftbukkit.item.Item.SLIME_BALL, Character.valueOf('P'), CraftBlock.PISTON});
        this.registerShapedRecipe(new ItemStack(org.bukkit.craftbukkit.item.Item.BED, 1), new Object[] { "###", "XXX", Character.valueOf('#'), CraftBlock.WOOL, Character.valueOf('X'), CraftBlock.WOOD});
        Collections.sort(this.b, new RecipeSorter(this));
        System.out.println(this.b.size() + " recipes");
    }

    public void registerShapedRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (aobject[i] instanceof String[]) {
            String[] astring = (String[]) ((String[]) aobject[i++]);

            for (int l = 0; l < astring.length; ++l) {
                String s1 = astring[l];

                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (aobject[i] instanceof String) {
                String s2 = (String) aobject[i++];

                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < aobject.length; i += 2) {
            Character character = (Character) aobject[i];
            ItemStack itemstack1 = null;

            if (aobject[i + 1] instanceof org.bukkit.craftbukkit.item.Item) {
                itemstack1 = new ItemStack((org.bukkit.craftbukkit.item.Item) aobject[i + 1]);
            } else if (aobject[i + 1] instanceof CraftBlock) {
                itemstack1 = new ItemStack((CraftBlock) aobject[i + 1], 1, -1);
            } else if (aobject[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) aobject[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).cloneItemStack();
            } else {
                aitemstack[i1] = null;
            }
        }

        this.b.add(new ShapedRecipes(j, k, aitemstack, itemstack));
    }

    public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) { // CraftBukkit - default -> public
        ArrayList arraylist = new ArrayList();
        Object[] aobject1 = aobject;
        int i = aobject.length;

        for (int j = 0; j < i; ++j) {
            Object object = aobject1[j];

            if (object instanceof ItemStack) {
                arraylist.add(((ItemStack) object).cloneItemStack());
            } else if (object instanceof org.bukkit.craftbukkit.item.Item) {
                arraylist.add(new ItemStack((Item) object));
            } else {
                if (!(object instanceof CraftBlock)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((CraftBlock) object));
            }
        }

        this.b.add(new ShapelessRecipes(itemstack, arraylist));
    }

    public ItemStack craft(InventoryCrafting inventorycrafting) {
        for (int i = 0; i < this.b.size(); ++i) {
            CraftingRecipe craftingrecipe = (CraftingRecipe) this.b.get(i);

            if (craftingrecipe.a(inventorycrafting)) {
                return craftingrecipe.b(inventorycrafting);
            }
        }

        return null;
    }

    public List b() {
        return this.b;
    }
}
