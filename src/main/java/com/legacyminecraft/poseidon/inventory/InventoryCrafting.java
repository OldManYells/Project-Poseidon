package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local crafting grid scaffold.
 */
public class InventoryCrafting implements IInventory {
    private final ItemStack[] items;

    public InventoryCrafting(Container container, int width, int height) {
        this.items = new ItemStack[width * height];
    }

    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < items.length ? items[index] : null;
    }

    @Override
    public ItemStack splitStack(int index, int amount) {
        ItemStack stack = getItem(index);
        if (stack == null) {
            return null;
        }
        return stack.a(amount);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index >= 0 && index < items.length) {
            items[index] = itemStack;
        }
    }

    @Override
    public String getName() {
        return "Crafting";
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean canPlayerUse(EntityHuman entityHuman) {
        return true;
    }

    @Override
    public ItemStack[] getContents() {
        return items;
    }
}
