package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local double chest composition scaffold.
 */
public class InventoryLargeChest implements IInventory {
    private final String name;
    private final IInventory left;
    private final IInventory right;

    public InventoryLargeChest(String name, IInventory left, IInventory right) {
        this.name = name == null ? "Large chest" : name;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getSize() {
        return left.getSize() + right.getSize();
    }

    @Override
    public ItemStack getItem(int index) {
        int leftSize = left.getSize();
        return index < leftSize ? left.getItem(index) : right.getItem(index - leftSize);
    }

    @Override
    public ItemStack splitStack(int index, int amount) {
        int leftSize = left.getSize();
        return index < leftSize ? left.splitStack(index, amount) : right.splitStack(index - leftSize, amount);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        int leftSize = left.getSize();
        if (index < leftSize) {
            left.setItem(index, itemStack);
        } else {
            right.setItem(index - leftSize, itemStack);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxStackSize() {
        return Math.min(left.getMaxStackSize(), right.getMaxStackSize());
    }

    @Override
    public void update() {
        left.update();
        right.update();
    }

    @Override
    public boolean canPlayerUse(EntityHuman entityHuman) {
        return left.canPlayerUse(entityHuman) && right.canPlayerUse(entityHuman);
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] merged = new ItemStack[getSize()];
        for (int i = 0; i < merged.length; i++) {
            merged[i] = getItem(i);
        }
        return merged;
    }
}
