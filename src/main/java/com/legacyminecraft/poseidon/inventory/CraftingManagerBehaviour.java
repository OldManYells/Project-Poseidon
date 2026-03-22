package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Block;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.RecipeIngots;
import net.minecraft.server.RecipesArmor;
import net.minecraft.server.RecipesCrafting;
import net.minecraft.server.RecipesDyes;
import net.minecraft.server.RecipesFood;
import net.minecraft.server.RecipesTools;
import net.minecraft.server.RecipesWeapons;
import net.minecraft.server.ShapedRecipes;
import net.minecraft.server.ShapelessRecipes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Canonical crafting-manager behaviour for recipe bootstrap and lookup.
 */
public final class CraftingManagerBehaviour {
    private static final CraftingManagerBehaviour INSTANCE = new CraftingManagerBehaviour();

    private CraftingManagerBehaviour() {
    }

    public static CraftingManagerBehaviour getInstance() {
        return INSTANCE;
    }

    public void bootstrapDefaultRecipes(CraftingManager manager, List recipes, Comparator sorter) {
        (new RecipesTools()).a(manager);
        (new RecipesWeapons()).a(manager);
        (new RecipeIngots()).a(manager);
        (new RecipesFood()).a(manager);
        (new RecipesCrafting()).a(manager);
        (new RecipesArmor()).a(manager);
        (new RecipesDyes()).a(manager);
        manager.registerShapedRecipe(new ItemStack(Item.PAPER, 3), new Object[] {"###", Character.valueOf('#'), Item.SUGAR_CANE});
        manager.registerShapedRecipe(new ItemStack(Item.BOOK, 1), new Object[] {"#", "#", "#", Character.valueOf('#'), Item.PAPER});
        manager.registerShapedRecipe(new ItemStack(Block.FENCE, 2), new Object[] {"###", "###", Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Block.JUKEBOX, 1), new Object[] {"###", "#X#", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.DIAMOND});
        manager.registerShapedRecipe(new ItemStack(Block.NOTE_BLOCK, 1), new Object[] {"###", "#X#", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.REDSTONE});
        manager.registerShapedRecipe(new ItemStack(Block.BOOKSHELF, 1), new Object[] {"###", "XXX", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.BOOK});
        manager.registerShapedRecipe(new ItemStack(Block.SNOW_BLOCK, 1), new Object[] {"##", "##", Character.valueOf('#'), Item.SNOW_BALL});
        manager.registerShapedRecipe(new ItemStack(Block.CLAY, 1), new Object[] {"##", "##", Character.valueOf('#'), Item.CLAY_BALL});
        manager.registerShapedRecipe(new ItemStack(Block.BRICK, 1), new Object[] {"##", "##", Character.valueOf('#'), Item.CLAY_BRICK});
        manager.registerShapedRecipe(new ItemStack(Block.GLOWSTONE, 1), new Object[] {"##", "##", Character.valueOf('#'), Item.GLOWSTONE_DUST});
        manager.registerShapedRecipe(new ItemStack(Block.WOOL, 1), new Object[] {"##", "##", Character.valueOf('#'), Item.STRING});
        manager.registerShapedRecipe(new ItemStack(Block.TNT, 1), new Object[] {"X#X", "#X#", "X#X", Character.valueOf('X'), Item.SULPHUR, Character.valueOf('#'), Block.SAND});
        manager.registerShapedRecipe(new ItemStack(Block.STEP, 3, 3), new Object[] {"###", Character.valueOf('#'), Block.COBBLESTONE});
        manager.registerShapedRecipe(new ItemStack(Block.STEP, 3, 0), new Object[] {"###", Character.valueOf('#'), Block.STONE});
        manager.registerShapedRecipe(new ItemStack(Block.STEP, 3, 1), new Object[] {"###", Character.valueOf('#'), Block.SANDSTONE});
        manager.registerShapedRecipe(new ItemStack(Block.STEP, 3, 2), new Object[] {"###", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.LADDER, 2), new Object[] {"# #", "###", "# #", Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Item.WOOD_DOOR, 1), new Object[] {"##", "##", "##", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.TRAP_DOOR, 2), new Object[] {"###", "###", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Item.IRON_DOOR, 1), new Object[] {"##", "##", "##", Character.valueOf('#'), Item.IRON_INGOT});
        manager.registerShapedRecipe(new ItemStack(Item.SIGN, 1), new Object[] {"###", "###", " X ", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Item.CAKE, 1), new Object[] {"AAA", "BEB", "CCC", Character.valueOf('A'), Item.MILK_BUCKET, Character.valueOf('B'), Item.SUGAR, Character.valueOf('C'), Item.WHEAT, Character.valueOf('E'), Item.EGG});
        manager.registerShapedRecipe(new ItemStack(Item.SUGAR, 1), new Object[] {"#", Character.valueOf('#'), Item.SUGAR_CANE});
        manager.registerShapedRecipe(new ItemStack(Block.WOOD, 4), new Object[] {"#", Character.valueOf('#'), Block.LOG});
        manager.registerShapedRecipe(new ItemStack(Item.STICK, 4), new Object[] {"#", "#", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[] {"X", "#", Character.valueOf('X'), Item.COAL, Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[] {"X", "#", Character.valueOf('X'), new ItemStack(Item.COAL, 1, 1), Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Item.BOWL, 4), new Object[] {"# #", " # ", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.RAILS, 16), new Object[] {"X X", "X#X", "X X", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Block.GOLDEN_RAIL, 6), new Object[] {"X X", "X#X", "XRX", Character.valueOf('X'), Item.GOLD_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('#'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Block.DETECTOR_RAIL, 6), new Object[] {"X X", "X#X", "XRX", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('#'), Block.STONE_PLATE});
        manager.registerShapedRecipe(new ItemStack(Item.MINECART, 1), new Object[] {"# #", "###", Character.valueOf('#'), Item.IRON_INGOT});
        manager.registerShapedRecipe(new ItemStack(Block.JACK_O_LANTERN, 1), new Object[] {"A", "B", Character.valueOf('A'), Block.PUMPKIN, Character.valueOf('B'), Block.TORCH});
        manager.registerShapedRecipe(new ItemStack(Item.STORAGE_MINECART, 1), new Object[] {"A", "B", Character.valueOf('A'), Block.CHEST, Character.valueOf('B'), Item.MINECART});
        manager.registerShapedRecipe(new ItemStack(Item.POWERED_MINECART, 1), new Object[] {"A", "B", Character.valueOf('A'), Block.FURNACE, Character.valueOf('B'), Item.MINECART});
        manager.registerShapedRecipe(new ItemStack(Item.BOAT, 1), new Object[] {"# #", "###", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Item.BUCKET, 1), new Object[] {"# #", " # ", Character.valueOf('#'), Item.IRON_INGOT});
        manager.registerShapedRecipe(new ItemStack(Item.FLINT_AND_STEEL, 1), new Object[] {"A ", " B", Character.valueOf('A'), Item.IRON_INGOT, Character.valueOf('B'), Item.FLINT});
        manager.registerShapedRecipe(new ItemStack(Item.BREAD, 1), new Object[] {"###", Character.valueOf('#'), Item.WHEAT});
        manager.registerShapedRecipe(new ItemStack(Block.WOOD_STAIRS, 4), new Object[] {"#  ", "## ", "###", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Item.FISHING_ROD, 1), new Object[] {"  #", " #X", "# X", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Item.STRING});
        manager.registerShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS, 4), new Object[] {"#  ", "## ", "###", Character.valueOf('#'), Block.COBBLESTONE});
        manager.registerShapedRecipe(new ItemStack(Item.PAINTING, 1), new Object[] {"###", "#X#", "###", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Block.WOOL});
        manager.registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1), new Object[] {"###", "#X#", "###", Character.valueOf('#'), Block.GOLD_BLOCK, Character.valueOf('X'), Item.APPLE});
        manager.registerShapedRecipe(new ItemStack(Block.LEVER, 1), new Object[] {"X", "#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.STICK});
        manager.registerShapedRecipe(new ItemStack(Block.REDSTONE_TORCH_ON, 1), new Object[] {"X", "#", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Item.REDSTONE});
        manager.registerShapedRecipe(new ItemStack(Item.DIODE, 1), new Object[] {"#X#", "III", Character.valueOf('#'), Block.REDSTONE_TORCH_ON, Character.valueOf('X'), Item.REDSTONE, Character.valueOf('I'), Block.STONE});
        manager.registerShapedRecipe(new ItemStack(Item.WATCH, 1), new Object[] {" # ", "#X#", " # ", Character.valueOf('#'), Item.GOLD_INGOT, Character.valueOf('X'), Item.REDSTONE});
        manager.registerShapedRecipe(new ItemStack(Item.COMPASS, 1), new Object[] {" # ", "#X#", " # ", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), Item.REDSTONE});
        manager.registerShapedRecipe(new ItemStack(Item.MAP, 1), new Object[] {"###", "#X#", "###", Character.valueOf('#'), Item.PAPER, Character.valueOf('X'), Item.COMPASS});
        manager.registerShapedRecipe(new ItemStack(Block.STONE_BUTTON, 1), new Object[] {"#", "#", Character.valueOf('#'), Block.STONE});
        manager.registerShapedRecipe(new ItemStack(Block.STONE_PLATE, 1), new Object[] {"##", Character.valueOf('#'), Block.STONE});
        manager.registerShapedRecipe(new ItemStack(Block.WOOD_PLATE, 1), new Object[] {"##", Character.valueOf('#'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.DISPENSER, 1), new Object[] {"###", "#X#", "#R#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.BOW, Character.valueOf('R'), Item.REDSTONE});
        manager.registerShapedRecipe(new ItemStack(Block.PISTON, 1), new Object[] {"TTT", "#X#", "#R#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('T'), Block.WOOD});
        manager.registerShapedRecipe(new ItemStack(Block.PISTON_STICKY, 1), new Object[] {"S", "P", Character.valueOf('S'), Item.SLIME_BALL, Character.valueOf('P'), Block.PISTON});
        manager.registerShapedRecipe(new ItemStack(Item.BED, 1), new Object[] {"###", "XXX", Character.valueOf('#'), Block.WOOL, Character.valueOf('X'), Block.WOOD});
        Collections.sort(recipes, sorter);
        System.out.println(recipes.size() + " recipes");
    }

    public void registerShapedRecipe(List recipes, ItemStack result, Object... recipeDefinition) {
        String pattern = "";
        int index = 0;
        int recipeWidth = 0;
        int recipeHeight = 0;

        if (recipeDefinition[index] instanceof String[]) {
            String[] rows = (String[]) recipeDefinition[index++];

            for (int rowIndex = 0; rowIndex < rows.length; ++rowIndex) {
                String row = rows[rowIndex];

                ++recipeHeight;
                recipeWidth = row.length();
                pattern = pattern + row;
            }
        } else {
            while (recipeDefinition[index] instanceof String) {
                String row = (String) recipeDefinition[index++];

                ++recipeHeight;
                recipeWidth = row.length();
                pattern = pattern + row;
            }
        }

        HashMap<Character, ItemStack> ingredientBySymbol;

        for (ingredientBySymbol = new HashMap<Character, ItemStack>(); index < recipeDefinition.length; index += 2) {
            Character symbol = (Character) recipeDefinition[index];
            ItemStack ingredientStack = null;

            if (recipeDefinition[index + 1] instanceof Item) {
                ingredientStack = new ItemStack((Item) recipeDefinition[index + 1]);
            } else if (recipeDefinition[index + 1] instanceof Block) {
                ingredientStack = new ItemStack((Block) recipeDefinition[index + 1], 1, -1);
            } else if (recipeDefinition[index + 1] instanceof ItemStack) {
                ingredientStack = (ItemStack) recipeDefinition[index + 1];
            }

            ingredientBySymbol.put(symbol, ingredientStack);
        }

        ItemStack[] gridStacks = new ItemStack[recipeWidth * recipeHeight];

        for (int gridIndex = 0; gridIndex < recipeWidth * recipeHeight; ++gridIndex) {
            char symbol = pattern.charAt(gridIndex);

            if (ingredientBySymbol.containsKey(Character.valueOf(symbol))) {
                gridStacks[gridIndex] = ((ItemStack) ingredientBySymbol.get(Character.valueOf(symbol))).cloneItemStack();
            } else {
                gridStacks[gridIndex] = null;
            }
        }

        recipes.add(new ShapedRecipes(recipeWidth, recipeHeight, gridStacks, result));
    }

    public void registerShapelessRecipe(List recipes, ItemStack result, Object... ingredients) {
        ArrayList ingredientStacks = new ArrayList();
        Object[] ingredientArray = ingredients;
        int ingredientCount = ingredients.length;

        for (int ingredientIndex = 0; ingredientIndex < ingredientCount; ++ingredientIndex) {
            Object ingredient = ingredientArray[ingredientIndex];

            if (ingredient instanceof ItemStack) {
                ingredientStacks.add(((ItemStack) ingredient).cloneItemStack());
            } else if (ingredient instanceof Item) {
                ingredientStacks.add(new ItemStack((Item) ingredient));
            } else {
                if (!(ingredient instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                ingredientStacks.add(new ItemStack((Block) ingredient));
            }
        }

        recipes.add(new ShapelessRecipes(result, ingredientStacks));
    }

    public ItemStack craft(List recipes, InventoryCrafting inventoryCrafting) {
        for (int recipeIndex = 0; recipeIndex < recipes.size(); ++recipeIndex) {
            CraftingRecipe recipe = (CraftingRecipe) recipes.get(recipeIndex);

            if (recipe.a(inventoryCrafting)) {
                return recipe.b(inventoryCrafting);
            }
        }

        return null;
    }

    public List recipes(List recipes) {
        return recipes;
    }
}
