package com.legacyminecraft.compat.bukkit;


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

    public void removeMaterialId(Object[] contents, int materialId, SlotClearer slotClearer) {
        for (int index = 0; index < contents.length; index++) {
            if (contents[index] != null
                    && ((Number) BridgeReflection.invoke(contents[index], "getTypeId")).intValue() == materialId) {
                slotClearer.clear(index);
            }
        }
    }

    public void removeMatchingItem(Object[] contents, Object item, SlotClearer slotClearer) {
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
