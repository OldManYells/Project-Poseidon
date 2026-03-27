package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local crafting result slot scaffold.
 */
public class SlotResult extends Slot {
    public SlotResult(EntityHuman owner, InventoryCrafting craftInventory, IInventory resultInventory, int slotIndex, int x, int y) {
        super(resultInventory, slotIndex, x, y);
    }
}
