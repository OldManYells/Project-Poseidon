package com.legacyminecraft.poseidon.item;

public final class ItemCoreBehaviour {
    private static final ItemCoreBehaviour INSTANCE = new ItemCoreBehaviour();

    private ItemCoreBehaviour() {
    }

    public static ItemCoreBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureId(int textureId) {
        return textureId;
    }

    public int resolveTextureIdFromGrid(int x, int y) {
        return x + y * 16;
    }

    public int filterData(int data) {
        return 0;
    }

    public void registerOrWarn(Object[] byId, int index) {
        if (byId[index] != null) {
            System.out.println("CONFLICT @ " + (index - 256));
        }
    }

    public String prefixedName(String name) {
        return "item." + name;
    }

    public boolean isDamageableUsable(int durability, boolean hasSubtypes) {
        return durability > 0 && !hasSubtypes;
    }

    public boolean hasCraftingResult(Object craftingResult) {
        return craftingResult != null;
    }

    public void validateCraftingResultAssignment(int maxStackSize) {
        if (maxStackSize > 1) {
            throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
        }
    }

    public String localizeItemName(String translationKey) {
        return com.legacyminecraft.compat.bukkit.StatisticCollector.a(translationKey + ".name");
    }
}
