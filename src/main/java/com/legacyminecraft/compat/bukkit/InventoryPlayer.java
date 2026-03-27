package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat inventory-player scaffold.
 */
public class InventoryPlayer implements IInventory {
    public final ItemStack[] items = new ItemStack[36];
    public final ItemStack[] armor = new ItemStack[4];
    public int itemInHandIndex;

    public static int e() {
        return 8;
    }

    public ItemStack getItemInHand() {
        return getItem(itemInHandIndex);
    }

    public ItemStack j() {
        return getItemInHand();
    }

    public boolean b(int itemId) {
        return false;
    }

    public boolean c(ItemStack itemStack) {
        return true;
    }

    public void b(ItemStack itemStack) {
        setItem(itemInHandIndex, itemStack);
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
        ItemStack itemStack = getItem(index);
        if (itemStack == null) {
            return null;
        }
        return itemStack.a(amount);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index >= 0 && index < items.length) {
            items[index] = itemStack;
        }
    }

    @Override
    public String getName() {
        return "Inventory";
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
