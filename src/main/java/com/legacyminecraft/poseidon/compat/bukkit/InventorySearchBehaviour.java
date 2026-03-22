package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.ItemStack;

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

    public boolean containsMaterialId(ItemStack[] contents, int materialId) {
        for (ItemStack item : contents) {
            if (item != null && item.getTypeId() == materialId) {
                return true;
            }
        }
        return false;
    }

    public boolean containsItem(ItemStack[] contents, ItemStack item) {
        if (item == null) {
            return false;
        }

        for (ItemStack stack : contents) {
            if (item.equals(stack)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsMaterialId(ItemStack[] contents, int materialId, int amount) {
        int currentAmount = 0;
        for (ItemStack item : contents) {
            if (item != null && item.getTypeId() == materialId) {
                currentAmount += item.getAmount();
            }
        }
        return currentAmount >= amount;
    }

    public boolean containsItem(ItemStack[] contents, ItemStack item, int amount) {
        if (item == null) {
            return false;
        }

        int currentAmount = 0;
        for (ItemStack stack : contents) {
            if (item.equals(stack)) {
                currentAmount += stack.getAmount();
            }
        }
        return currentAmount >= amount;
    }

    public HashMap<Integer, ItemStack> allByMaterialId(ItemStack[] contents, int materialId) {
        HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
        for (int index = 0; index < contents.length; index++) {
            ItemStack item = contents[index];
            if (item != null && item.getTypeId() == materialId) {
                slots.put(index, item);
            }
        }
        return slots;
    }

    public HashMap<Integer, ItemStack> allByItem(ItemStack[] contents, ItemStack item) {
        HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
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

    public int firstByMaterialId(ItemStack[] contents, int materialId) {
        for (int index = 0; index < contents.length; index++) {
            ItemStack item = contents[index];
            if (item != null && item.getTypeId() == materialId) {
                return index;
            }
        }
        return -1;
    }

    public int firstByItem(ItemStack[] contents, ItemStack item) {
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

    public int firstEmpty(ItemStack[] contents) {
        for (int index = 0; index < contents.length; index++) {
            if (contents[index] == null) {
                return index;
            }
        }
        return -1;
    }

    public int firstPartialByMaterialId(ItemStack[] contents, int materialId) {
        for (int index = 0; index < contents.length; index++) {
            ItemStack item = contents[index];
            if (item != null && item.getTypeId() == materialId && item.getAmount() < item.getMaxStackSize()) {
                return index;
            }
        }
        return -1;
    }

    public int firstPartialByItem(ItemStack[] contents, ItemStack item) {
        if (item == null) {
            return -1;
        }

        for (int index = 0; index < contents.length; index++) {
            ItemStack contentItem = contents[index];
            if (contentItem != null
                    && contentItem.getTypeId() == item.getTypeId()
                    && contentItem.getAmount() < contentItem.getMaxStackSize()
                    && contentItem.getDurability() == item.getDurability()) {
                return index;
            }
        }
        return -1;
    }
}
