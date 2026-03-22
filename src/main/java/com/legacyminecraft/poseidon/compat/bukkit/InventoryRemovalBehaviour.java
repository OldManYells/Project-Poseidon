package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.ItemStack;

/**
 * Canonical behaviour for CraftInventory remove/clear orchestration loops.
 */
public final class InventoryRemovalBehaviour {
    private static final InventoryRemovalBehaviour INSTANCE = new InventoryRemovalBehaviour();

    private InventoryRemovalBehaviour() {
    }

    public static InventoryRemovalBehaviour getInstance() {
        return INSTANCE;
    }

    public void removeMaterialId(ItemStack[] contents, int materialId, SlotClearer slotClearer) {
        for (int index = 0; index < contents.length; index++) {
            if (contents[index] != null && contents[index].getTypeId() == materialId) {
                slotClearer.clear(index);
            }
        }
    }

    public void removeMatchingItem(ItemStack[] contents, ItemStack item, SlotClearer slotClearer) {
        for (int index = 0; index < contents.length; index++) {
            if (contents[index] != null && contents[index].equals(item)) {
                slotClearer.clear(index);
            }
        }
    }

    public void clearAll(int size, SlotClearer slotClearer) {
        for (int index = 0; index < size; index++) {
            slotClearer.clear(index);
        }
    }

    public interface SlotClearer {
        void clear(int index);
    }
}
