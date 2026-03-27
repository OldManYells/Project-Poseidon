package com.legacyminecraft.poseidon.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory-local container scaffold.
 */
public class Container {
    public final List e = new ArrayList();
    public int windowId;

    public void poseidonAddSlot(Slot slot) {
        e.add(slot);
    }

    public void poseidonMergeItemStack(ItemStack itemStack, int start, int end, boolean reverse) {
    }

    public ItemStack a(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= e.size()) {
            return null;
        }
        return ((Slot) e.get(slotIndex)).getItem();
    }

    public ItemStack a(int slotIndex, int amount, boolean flag, EntityHuman player) {
        if (slotIndex < 0 || slotIndex >= e.size()) {
            return null;
        }
        return ((Slot) e.get(slotIndex)).a(amount);
    }

    public Slot a(InventoryPlayer inventoryPlayer, int slotIndex) {
        if (slotIndex < 0 || slotIndex >= e.size()) {
            return new Slot();
        }
        return (Slot) e.get(slotIndex);
    }

    public void a(ICrafting listener) {
    }

    public void a(EntityHuman player) {
    }

    public void a() {
    }

    public void a(EntityPlayer player, boolean accepted) {
    }

    public boolean c(EntityPlayer player) {
        return true;
    }
}
