package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyInventoryWrapperThinnessTest {
    private static final Path CONTAINER_FURNACE_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerFurnace.java");
    private static final Path CONTAINER_WORKBENCH_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerWorkbench.java");
    private static final Path CONTAINER_PLAYER_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerPlayer.java");
    private static final Path CONTAINER_CHEST_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerChest.java");
    private static final Path CONTAINER_DISPENSER_PATH = Paths.get("src/main/java/net/minecraft/server/ContainerDispenser.java");
    private static final Path CONTAINER_PATH = Paths.get("src/main/java/net/minecraft/server/Container.java");
    private static final Path SLOT_RESULT_PATH = Paths.get("src/main/java/net/minecraft/server/SlotResult.java");
    private static final Path SLOT_RESULT2_PATH = Paths.get("src/main/java/net/minecraft/server/SlotResult2.java");
    private static final Path SLOT_ARMOR_PATH = Paths.get("src/main/java/net/minecraft/server/SlotArmor.java");
    private static final Path SLOT_PATH = Paths.get("src/main/java/net/minecraft/server/Slot.java");
    private static final Path FURNACE_RECIPES_PATH = Paths.get("src/main/java/net/minecraft/server/FurnaceRecipes.java");
    private static final Path RECIPE_SORTER_PATH = Paths.get("src/main/java/net/minecraft/server/RecipeSorter.java");
    private static final Path RECIPE_INGOTS_PATH = Paths.get("src/main/java/net/minecraft/server/RecipeIngots.java");
    private static final Path RECIPES_FOOD_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesFood.java");
    private static final Path RECIPES_CRAFTING_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesCrafting.java");
    private static final Path RECIPES_ARMOR_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesArmor.java");
    private static final Path RECIPES_TOOLS_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesTools.java");
    private static final Path RECIPES_WEAPONS_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesWeapons.java");
    private static final Path RECIPES_DYES_PATH = Paths.get("src/main/java/net/minecraft/server/RecipesDyes.java");
    private static final Path SHAPED_RECIPES_PATH = Paths.get("src/main/java/net/minecraft/server/ShapedRecipes.java");
    private static final Path SHAPELESS_RECIPES_PATH = Paths.get("src/main/java/net/minecraft/server/ShapelessRecipes.java");

    @Test
    public void containerFurnaceDelegatesInventoryFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CONTAINER_FURNACE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FurnaceContainerBehaviour"));
        Assert.assertTrue(text.contains("FURNACE_CONTAINER_BEHAVIOUR.initializeSlots"));
        Assert.assertTrue(text.contains("FURNACE_CONTAINER_BEHAVIOUR.sendInitialProgress"));
        Assert.assertTrue(text.contains("FURNACE_CONTAINER_BEHAVIOUR.broadcastProgress"));
        Assert.assertTrue(text.contains("FURNACE_CONTAINER_BEHAVIOUR.quickMove"));
        Assert.assertFalse(text.contains("new Slot(tileentityfurnace, 0, 56, 17)"));
        Assert.assertFalse(text.contains("if (this.b != this.a.cookTime)"));
        Assert.assertFalse(text.contains("Slot slot = (Slot) this.e.get(i)"));
    }

    @Test
    public void containerExposesCanonicalBridgeMethodsForSlotAndMergeOperations() throws IOException {
        String text = new String(Files.readAllBytes(CONTAINER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("public final void poseidonAddSlot"));
        Assert.assertTrue(text.contains("public final void poseidonMergeItemStack"));
    }

    @Test
    public void containerWorkbenchDelegatesCraftingAndTransferFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CONTAINER_WORKBENCH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorkbenchContainerBehaviour"));
        Assert.assertTrue(text.contains("WORKBENCH_CONTAINER_BEHAVIOUR.initializeSlots"));
        Assert.assertTrue(text.contains("WORKBENCH_CONTAINER_BEHAVIOUR.updateCraftResult"));
        Assert.assertTrue(text.contains("WORKBENCH_CONTAINER_BEHAVIOUR.onClose"));
        Assert.assertTrue(text.contains("WORKBENCH_CONTAINER_BEHAVIOUR.canUse"));
        Assert.assertTrue(text.contains("WORKBENCH_CONTAINER_BEHAVIOUR.quickMove"));
        Assert.assertFalse(text.contains("CraftingManager.getInstance().craft(this.craftInventory)"));
        Assert.assertFalse(text.contains("this.c.getTypeId(this.h, this.i, this.j) != Block.WORKBENCH.id"));
        Assert.assertFalse(text.contains("Slot slot = (Slot) this.e.get(i)"));
    }

    @Test
    public void containerPlayerDelegatesCraftingArmorAndTransferFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CONTAINER_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerContainerBehaviour"));
        Assert.assertTrue(text.contains("PLAYER_CONTAINER_BEHAVIOUR.initializeSlots"));
        Assert.assertTrue(text.contains("PLAYER_CONTAINER_BEHAVIOUR.updateCraftResult"));
        Assert.assertTrue(text.contains("PLAYER_CONTAINER_BEHAVIOUR.onClose"));
        Assert.assertTrue(text.contains("PLAYER_CONTAINER_BEHAVIOUR.quickMove"));
        Assert.assertFalse(text.contains("new InventoryCrafting(this, 2, 2)"));
        Assert.assertFalse(text.contains("new SlotArmor(this, inventoryplayer"));
        Assert.assertFalse(text.contains("CraftingManager.getInstance().craft(this.craftInventory)"));
        Assert.assertFalse(text.contains("Slot slot = (Slot) this.e.get(i)"));
    }

    @Test
    public void containerChestAndDispenserDelegateSlotLayoutAndAccessPoliciesToCanonicalBehaviours() throws IOException {
        String containerChest = new String(Files.readAllBytes(CONTAINER_CHEST_PATH), StandardCharsets.UTF_8);
        String containerDispenser = new String(Files.readAllBytes(CONTAINER_DISPENSER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(containerChest.contains("ChestContainerBehaviour"));
        Assert.assertTrue(containerChest.contains("CHEST_CONTAINER_BEHAVIOUR.initializeSlots"));
        Assert.assertTrue(containerChest.contains("CHEST_CONTAINER_BEHAVIOUR.canUse"));
        Assert.assertTrue(containerChest.contains("CHEST_CONTAINER_BEHAVIOUR.quickMove"));
        Assert.assertFalse(containerChest.contains("this.a(new Slot(iinventory1, k + j * 9"));
        Assert.assertFalse(containerChest.contains("Slot slot = (Slot) this.e.get(i)"));

        Assert.assertTrue(containerDispenser.contains("DispenserContainerBehaviour"));
        Assert.assertTrue(containerDispenser.contains("DISPENSER_CONTAINER_BEHAVIOUR.initializeSlots"));
        Assert.assertTrue(containerDispenser.contains("DISPENSER_CONTAINER_BEHAVIOUR.canUse"));
        Assert.assertFalse(containerDispenser.contains("this.a(new Slot(tileentitydispenser, j + i * 3"));
    }

    @Test
    public void slotWrappersDelegateResultAndArmorPoliciesToCanonicalBehaviours() throws IOException {
        String slotResult = new String(Files.readAllBytes(SLOT_RESULT_PATH), StandardCharsets.UTF_8);
        String slotResult2 = new String(Files.readAllBytes(SLOT_RESULT2_PATH), StandardCharsets.UTF_8);
        String slotArmor = new String(Files.readAllBytes(SLOT_ARMOR_PATH), StandardCharsets.UTF_8);
        String slot = new String(Files.readAllBytes(SLOT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(slotResult.contains("CraftingResultSlotBehaviour"));
        Assert.assertTrue(slotResult.contains("CRAFTING_RESULT_SLOT_BEHAVIOUR.onCrafted"));
        Assert.assertFalse(slotResult.contains("itemstack.id == Block.WORKBENCH.id"));
        Assert.assertFalse(slotResult.contains("this.d.splitStack(i, 1)"));

        Assert.assertTrue(slotResult2.contains("FurnaceResultSlotBehaviour"));
        Assert.assertTrue(slotResult2.contains("FURNACE_RESULT_SLOT_BEHAVIOUR.onSmelted"));
        Assert.assertFalse(slotResult2.contains("itemstack.id == Item.IRON_INGOT.id"));

        Assert.assertTrue(slotArmor.contains("ArmorSlotAcceptanceBehaviour"));
        Assert.assertTrue(slotArmor.contains("ARMOR_SLOT_ACCEPTANCE_BEHAVIOUR.isAllowed"));
        Assert.assertFalse(slotArmor.contains("itemstack.getItem() instanceof ItemArmor ? ((ItemArmor) itemstack.getItem()).bk == this.d"));

        Assert.assertTrue(slot.contains("SlotInteractionBehaviour"));
        Assert.assertTrue(slot.contains("SLOT_INTERACTION_BEHAVIOUR.getItem"));
        Assert.assertTrue(slot.contains("SLOT_INTERACTION_BEHAVIOUR.splitStack"));
        Assert.assertFalse(slot.contains("return this.inventory.getItem(this.index)"));
        Assert.assertFalse(slot.contains("return iinventory == this.inventory && i == this.index"));
    }

    @Test
    public void furnaceRecipesDelegatesRecipeBootstrapAndLookupToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(FURNACE_RECIPES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FurnaceRecipeRegistryBehaviour"));
        Assert.assertTrue(text.contains("FURNACE_RECIPE_REGISTRY_BEHAVIOUR.registerDefaultRecipes"));
        Assert.assertTrue(text.contains("FURNACE_RECIPE_REGISTRY_BEHAVIOUR.registerRecipe"));
        Assert.assertTrue(text.contains("FURNACE_RECIPE_REGISTRY_BEHAVIOUR.getRecipe"));
        Assert.assertTrue(text.contains("FURNACE_RECIPE_REGISTRY_BEHAVIOUR.getRecipes"));
        Assert.assertFalse(text.contains("this.registerRecipe(Block.IRON_ORE.id"));
        Assert.assertFalse(text.contains("this.b.put(Integer.valueOf(i), itemstack)"));
    }

    @Test
    public void recipeSorterDelegatesRecipeOrderingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(RECIPE_SORTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeOrderingBehaviour"));
        Assert.assertTrue(text.contains("RECIPE_ORDERING_BEHAVIOUR.compare"));
        Assert.assertFalse(text.contains("craftingrecipe instanceof ShapelessRecipes"));
    }

    @Test
    public void recipeIngotsDelegatesRecipeTableAndRegistrationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(RECIPE_INGOTS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("IngotBlockRecipeRegistrationBehaviour"));
        Assert.assertTrue(text.contains("INGOT_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.createRecipePairs"));
        Assert.assertTrue(text.contains("INGOT_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.registerAll"));
        Assert.assertFalse(text.contains("this.a = new Object[][]"));
        Assert.assertFalse(text.contains("craftingmanager.registerShapedRecipe(new ItemStack(block)"));
    }

    @Test
    public void recipesFoodAndCraftingDelegateRegistrationToCanonicalBehaviours() throws IOException {
        String recipesFood = new String(Files.readAllBytes(RECIPES_FOOD_PATH), StandardCharsets.UTF_8);
        String recipesCrafting = new String(Files.readAllBytes(RECIPES_CRAFTING_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(recipesFood.contains("FoodRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesFood.contains("FOOD_RECIPE_REGISTRATION_BEHAVIOUR.registerAll"));
        Assert.assertFalse(recipesFood.contains("craftingmanager.registerShapedRecipe(new ItemStack(Item.MUSHROOM_SOUP)"));

        Assert.assertTrue(recipesCrafting.contains("CraftingBlockRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesCrafting.contains("CRAFTING_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.registerAll"));
        Assert.assertFalse(recipesCrafting.contains("craftingmanager.registerShapedRecipe(new ItemStack(Block.CHEST)"));
    }

    @Test
    public void recipesArmorToolsAndWeaponsDelegateRegistrationToCanonicalBehaviour() throws IOException {
        String recipesArmor = new String(Files.readAllBytes(RECIPES_ARMOR_PATH), StandardCharsets.UTF_8);
        String recipesTools = new String(Files.readAllBytes(RECIPES_TOOLS_PATH), StandardCharsets.UTF_8);
        String recipesWeapons = new String(Files.readAllBytes(RECIPES_WEAPONS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(recipesArmor.contains("EquipmentRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesArmor.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createArmorPatterns"));
        Assert.assertTrue(recipesArmor.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerArmor"));
        Assert.assertFalse(recipesArmor.contains("this.a = new String[][]"));
        Assert.assertFalse(recipesArmor.contains("craftingmanager.registerShapedRecipe(new ItemStack(item)"));

        Assert.assertTrue(recipesTools.contains("EquipmentRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesTools.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createToolTable"));
        Assert.assertTrue(recipesTools.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerTools"));
        Assert.assertFalse(recipesTools.contains("craftingmanager.registerShapedRecipe(new ItemStack(Item.SHEARS)"));

        Assert.assertTrue(recipesWeapons.contains("EquipmentRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesWeapons.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createWeaponPatterns"));
        Assert.assertTrue(recipesWeapons.contains("EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerWeapons"));
        Assert.assertFalse(recipesWeapons.contains("craftingmanager.registerShapedRecipe(new ItemStack(Item.BOW, 1)"));
    }

    @Test
    public void recipesDyesDelegatesRegistrationToCanonicalBehaviour() throws IOException {
        String recipesDyes = new String(Files.readAllBytes(RECIPES_DYES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(recipesDyes.contains("DyeRecipeRegistrationBehaviour"));
        Assert.assertTrue(recipesDyes.contains("DYE_RECIPE_REGISTRATION_BEHAVIOUR.registerAll"));
        Assert.assertFalse(recipesDyes.contains("for (int i = 0; i < 16; ++i)"));
        Assert.assertFalse(recipesDyes.contains("craftingmanager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 11)"));
    }

    @Test
    public void shapedAndShapelessRecipesDelegateMatchingAndCraftResultPoliciesToCanonicalBehaviours() throws IOException {
        String shapedRecipes = new String(Files.readAllBytes(SHAPED_RECIPES_PATH), StandardCharsets.UTF_8);
        String shapelessRecipes = new String(Files.readAllBytes(SHAPELESS_RECIPES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(shapedRecipes.contains("ShapedRecipeMatchingBehaviour"));
        Assert.assertTrue(shapedRecipes.contains("SHAPED_RECIPE_MATCHING_BEHAVIOUR.matches"));
        Assert.assertTrue(shapedRecipes.contains("SHAPED_RECIPE_MATCHING_BEHAVIOUR.craftResult"));
        Assert.assertFalse(shapedRecipes.contains("for (int i = 0; i <= 3 - this.b; ++i)"));
        Assert.assertFalse(shapedRecipes.contains("if (this.a(inventorycrafting, i, j, true))"));

        Assert.assertTrue(shapelessRecipes.contains("ShapelessRecipeMatchingBehaviour"));
        Assert.assertTrue(shapelessRecipes.contains("SHAPELESS_RECIPE_MATCHING_BEHAVIOUR.matches"));
        Assert.assertTrue(shapelessRecipes.contains("SHAPELESS_RECIPE_MATCHING_BEHAVIOUR.craftResult"));
        Assert.assertFalse(shapelessRecipes.contains("ArrayList arraylist = new ArrayList(this.b)"));
        Assert.assertFalse(shapelessRecipes.contains("Iterator iterator = arraylist.iterator()"));
    }
}
