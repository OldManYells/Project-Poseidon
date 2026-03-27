package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local furnace tile scaffold.
 */
public class TileEntityFurnace implements IInventory {
    private final ItemStack[] items = new ItemStack[3];
    public int burnTime;
    public int cookTime;
    public int ticksForCurrentFuel;

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
        return stack == null ? null : stack.a(amount);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index >= 0 && index < items.length) {
            items[index] = itemStack;
        }
    }

    @Override
    public String getName() {
        return "Furnace";
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

    public boolean a_(EntityHuman entityHuman) {
        return true;
    }

    @Override
    public ItemStack[] getContents() {
        return items;
    }
}
