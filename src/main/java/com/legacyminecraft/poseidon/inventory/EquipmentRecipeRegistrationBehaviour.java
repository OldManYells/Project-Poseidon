package com.legacyminecraft.poseidon.inventory;


public final class EquipmentRecipeRegistrationBehaviour {
    private static final EquipmentRecipeRegistrationBehaviour INSTANCE = new EquipmentRecipeRegistrationBehaviour();

    private EquipmentRecipeRegistrationBehaviour() {
    }

    public static EquipmentRecipeRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public String[][] createArmorPatterns() {
        return new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    }

    public Object[][] createArmorTable() {
        return new Object[][] {
                {Item.LEATHER, Block.FIRE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT},
                {Item.LEATHER_HELMET, Item.CHAINMAIL_HELMET, Item.IRON_HELMET, Item.DIAMOND_HELMET, Item.GOLD_HELMET},
                {Item.LEATHER_CHESTPLATE, Item.CHAINMAIL_CHESTPLATE, Item.IRON_CHESTPLATE, Item.DIAMOND_CHESTPLATE, Item.GOLD_CHESTPLATE},
                {Item.LEATHER_LEGGINGS, Item.CHAINMAIL_LEGGINGS, Item.IRON_LEGGINGS, Item.DIAMOND_LEGGINGS, Item.GOLD_LEGGINGS},
                {Item.LEATHER_BOOTS, Item.CHAINMAIL_BOOTS, Item.IRON_BOOTS, Item.DIAMOND_BOOTS, Item.GOLD_BOOTS}
        };
    }

    public void registerArmor(CraftingManager craftingmanager, String[][] patterns, Object[][] table) {
        for (int i = 0; i < table[0].length; ++i) {
            Object material = table[0][i];

            for (int j = 0; j < table.length - 1; ++j) {
                Item item = (Item) table[j + 1][i];
                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] {patterns[j], Character.valueOf('X'), material});
            }
        }
    }

    public String[][] createToolPatterns() {
        return new String[][] {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    }

    public Object[][] createToolTable() {
        return new Object[][] {
                {Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT},
                {Item.WOOD_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLD_PICKAXE},
                {Item.WOOD_SPADE, Item.STONE_SPADE, Item.IRON_SPADE, Item.DIAMOND_SPADE, Item.GOLD_SPADE},
                {Item.WOOD_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLD_AXE},
                {Item.WOOD_HOE, Item.STONE_HOE, Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLD_HOE}
        };
    }

    public void registerTools(CraftingManager craftingmanager, String[][] patterns, Object[][] table) {
        for (int i = 0; i < table[0].length; ++i) {
            Object material = table[0][i];

            for (int j = 0; j < table.length - 1; ++j) {
                Item item = (Item) table[j + 1][i];
                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] {patterns[j], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), material});
            }
        }

        craftingmanager.registerShapedRecipe(new ItemStack(Item.SHEARS), new Object[] {" #", "# ", Character.valueOf('#'), Item.IRON_INGOT});
    }

    public String[][] createWeaponPatterns() {
        return new String[][] {{"X", "X", "#"}};
    }

    public Object[][] createWeaponTable() {
        return new Object[][] {
                {Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT},
                {Item.WOOD_SWORD, Item.STONE_SWORD, Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLD_SWORD}
        };
    }

    public void registerWeapons(CraftingManager craftingmanager, String[][] patterns, Object[][] table) {
        for (int i = 0; i < table[0].length; ++i) {
            Object material = table[0][i];

            for (int j = 0; j < table.length - 1; ++j) {
                Item item = (Item) table[j + 1][i];
                craftingmanager.registerShapedRecipe(new ItemStack(item), new Object[] {patterns[j], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), material});
            }
        }

        craftingmanager.registerShapedRecipe(new ItemStack(Item.BOW, 1), new Object[] {" #X", "# X", " #X", Character.valueOf('X'), Item.STRING, Character.valueOf('#'), Item.STICK});
        craftingmanager.registerShapedRecipe(new ItemStack(Item.ARROW, 4), new Object[] {"X", "#", "Y", Character.valueOf('Y'), Item.FEATHER, Character.valueOf('X'), Item.FLINT, Character.valueOf('#'), Item.STICK});
    }
}
