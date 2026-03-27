package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local slot scaffold.
 */
public class Slot {
    public int a;
    protected final IInventory inventory;
    protected final int slotIndex;
    protected final int x;
    protected final int y;

    public Slot() {
        this(new InventoryCraftResult(), 0, 0, 0);
    }

    public Slot(IInventory inventory, int slotIndex, int x, int y) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.x = x;
        this.y = y;
    }

    public boolean a(IInventory expectedInventory, int expectedIndex) {
        return expectedInventory == this.inventory && expectedIndex == this.slotIndex;
    }

    public boolean b() {
        return getItem() != null;
    }

    public ItemStack getItem() {
        return inventory.getItem(slotIndex);
    }

    public void c(ItemStack itemStack) {
        inventory.setItem(slotIndex, itemStack);
    }

    public void c() {
        inventory.update();
    }

    public ItemStack a(int amount) {
        return inventory.splitStack(slotIndex, amount);
    }

    public int d() {
        return inventory.getMaxStackSize();
    }

    public void a(ItemStack itemStack) {
    }

    public boolean isAllowed(ItemStack itemStack) {
        return true;
    }
}
