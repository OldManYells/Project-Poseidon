package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public final class SlotInteractionBehaviour {
    private static final SlotInteractionBehaviour INSTANCE = new SlotInteractionBehaviour();

    private SlotInteractionBehaviour() {
    }

    public static SlotInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public void onSet() {
        // Base slot behaviour only marks inventory dirty via the wrapper callback.
    }

    public boolean isAllowed(ItemStack itemstack) {
        return true;
    }

    public ItemStack getItem(IInventory inventory, int index) {
        return inventory.getItem(index);
    }

    public boolean hasItem(ItemStack itemstack) {
        return itemstack != null;
    }

    public void setItem(IInventory inventory, int index, ItemStack itemstack) {
        inventory.setItem(index, itemstack);
    }

    public void onInventoryChanged(IInventory inventory) {
        inventory.update();
    }

    public int getMaxStackSize(IInventory inventory) {
        return inventory.getMaxStackSize();
    }

    public ItemStack splitStack(IInventory inventory, int index, int amount) {
        return inventory.splitStack(index, amount);
    }

    public boolean matchesInventorySlot(IInventory expectedInventory, int expectedIndex, IInventory inventory, int index) {
        return inventory == expectedInventory && index == expectedIndex;
    }
}
