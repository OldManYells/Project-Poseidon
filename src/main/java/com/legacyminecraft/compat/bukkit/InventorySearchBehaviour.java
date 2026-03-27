package com.legacyminecraft.compat.bukkit;


import java.util.HashMap;

/**
 * Canonical behaviour for CraftInventory item search/query loops.
 */
public final class InventorySearchBehaviour {
    private static final InventorySearchBehaviour INSTANCE = new InventorySearchBehaviour();

    private InventorySearchBehaviour() {
    }

    public static InventorySearchBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean containsMaterialId(Object[] contents, int materialId) {
        for (Object item : contents) {
            if (item != null && ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == materialId) {
                return true;
            }
        }
        return false;
    }

    public boolean containsItem(Object[] contents, Object item) {
        if (item == null) {
            return false;
        }

        for (Object stack : contents) {
            if (item.equals(stack)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsMaterialId(Object[] contents, int materialId, int amount) {
        int currentAmount = 0;
        for (Object item : contents) {
            if (item != null && ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == materialId) {
                currentAmount += ((Number) BridgeReflection.invoke(item, "getAmount")).intValue();
            }
        }
        return currentAmount >= amount;
    }

    public boolean containsItem(Object[] contents, Object item, int amount) {
        if (item == null) {
            return false;
        }

        int currentAmount = 0;
        for (Object stack : contents) {
            if (item.equals(stack)) {
                currentAmount += ((Number) BridgeReflection.invoke(stack, "getAmount")).intValue();
            }
        }
        return currentAmount >= amount;
    }

    public HashMap<Integer, Object> allByMaterialId(Object[] contents, int materialId) {
        HashMap<Integer, Object> slots = new HashMap<Integer, Object>();
        for (int index = 0; index < contents.length; index++) {
            Object item = contents[index];
            if (item != null && ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == materialId) {
                slots.put(index, item);
            }
        }
        return slots;
    }

    public HashMap<Integer, Object> allByItem(Object[] contents, Object item) {
        HashMap<Integer, Object> slots = new HashMap<Integer, Object>();
        if (item == null) {
            return slots;
        }

        for (int index = 0; index < contents.length; index++) {
            if (item.equals(contents[index])) {
                slots.put(index, contents[index]);
            }
        }
        return slots;
    }

    public int firstByMaterialId(Object[] contents, int materialId) {
        for (int index = 0; index < contents.length; index++) {
            Object item = contents[index];
            if (item != null && ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == materialId) {
                return index;
            }
        }
        return -1;
    }

    public int firstByItem(Object[] contents, Object item) {
        if (item == null) {
            return -1;
        }

        for (int index = 0; index < contents.length; index++) {
            if (item.equals(contents[index])) {
                return index;
            }
        }
        return -1;
    }

    public int firstEmpty(Object[] contents) {
        for (int index = 0; index < contents.length; index++) {
            if (contents[index] == null) {
                return index;
            }
        }
        return -1;
    }

    public int firstPartialByMaterialId(Object[] contents, int materialId) {
        for (int index = 0; index < contents.length; index++) {
            Object item = contents[index];
            if (item != null
                    && ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() == materialId
                    && ((Number) BridgeReflection.invoke(item, "getAmount")).intValue() < ((Number) BridgeReflection.invoke(item, "getMaxStackSize")).intValue()) {
                return index;
            }
        }
        return -1;
    }

    public int firstPartialByItem(Object[] contents, Object item) {
        if (item == null) {
            return -1;
        }

        for (int index = 0; index < contents.length; index++) {
            Object contentItem = contents[index];
            if (contentItem != null
                    && ((Number) BridgeReflection.invoke(contentItem, "getTypeId")).intValue() == ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue()
                    && ((Number) BridgeReflection.invoke(contentItem, "getAmount")).intValue() < ((Number) BridgeReflection.invoke(contentItem, "getMaxStackSize")).intValue()
                    && ((Number) BridgeReflection.invoke(contentItem, "getDurability")).shortValue() == ((Number) BridgeReflection.invoke(item, "getDurability")).shortValue()) {
                return index;
            }
        }
        return -1;
    }
}
