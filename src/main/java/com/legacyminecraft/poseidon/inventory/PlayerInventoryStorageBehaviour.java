package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical storage and slot-routing operations for legacy InventoryPlayer wrappers.
 */
public final class PlayerInventoryStorageBehaviour {
    private static final PlayerInventoryStorageBehaviour INSTANCE = new PlayerInventoryStorageBehaviour();
    private static final int HOTBAR_SIZE = 9;

    private PlayerInventoryStorageBehaviour() {
    }

    public static PlayerInventoryStorageBehaviour getInstance() {
        return INSTANCE;
    }

    public int hotbarSize() {
        return HOTBAR_SIZE;
    }

    public ItemStack getItemInHand(ItemStack[] items, int itemInHandIndex) {
        return itemInHandIndex < HOTBAR_SIZE && itemInHandIndex >= 0 ? items[itemInHandIndex] : null;
    }

    public int findSlotByItemId(ItemStack[] items, int itemId) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].id == itemId) {
                return i;
            }
        }
        return -1;
    }

    public int findFirstPartial(ItemStack[] items, ItemStack itemStack, int maxStackSize) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null
                    && items[i].id == itemStack.id
                    && items[i].isStackable()
                    && items[i].count < items[i].getMaxStackSize()
                    && items[i].count < maxStackSize
                    && (!items[i].usesData() || items[i].getData() == itemStack.getData())) {
                return i;
            }
        }
        return -1;
    }

    public int firstEmptySlot(ItemStack[] items) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int canHold(ItemStack[] items, ItemStack itemStack, int maxStackSize) {
        int remains = itemStack.count;
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                return itemStack.count;
            }

            if (items[i] != null
                    && items[i].id == itemStack.id
                    && items[i].isStackable()
                    && items[i].count < items[i].getMaxStackSize()
                    && items[i].count < maxStackSize
                    && (!items[i].usesData() || items[i].getData() == itemStack.getData())) {
                int stackCap = items[i].getMaxStackSize() < maxStackSize ? items[i].getMaxStackSize() : maxStackSize;
                remains -= stackCap - items[i].count;
            }
            if (remains <= 0) {
                return itemStack.count;
            }
        }
        return itemStack.count - remains;
    }

    public boolean consumeByItemId(ItemStack[] items, int itemId) {
        int index = findSlotByItemId(items, itemId);
        if (index < 0) {
            return false;
        }

        if (--items[index].count <= 0) {
            items[index] = null;
        }
        return true;
    }

    public boolean pickup(ItemStack[] items, ItemStack itemStack, int maxStackSize) {
        int previousCount;
        if (itemStack.f()) {
            int emptySlot = firstEmptySlot(items);
            if (emptySlot >= 0) {
                items[emptySlot] = ItemStack.b(itemStack);
                items[emptySlot].b = 5;
                itemStack.count = 0;
                return true;
            }
            return false;
        }

        do {
            previousCount = itemStack.count;
            itemStack.count = storePartial(items, itemStack, maxStackSize);
        } while (itemStack.count > 0 && itemStack.count < previousCount);

        return itemStack.count < previousCount;
    }

    private int storePartial(ItemStack[] items, ItemStack itemStack, int maxStackSize) {
        int itemId = itemStack.id;
        int remaining = itemStack.count;
        int slotIndex = findFirstPartial(items, itemStack, maxStackSize);
        if (slotIndex < 0) {
            slotIndex = firstEmptySlot(items);
        }
        if (slotIndex < 0) {
            return remaining;
        }

        if (items[slotIndex] == null) {
            items[slotIndex] = new ItemStack(itemId, 0, itemStack.getData());
        }

        int toMove = remaining;
        if (toMove > items[slotIndex].getMaxStackSize() - items[slotIndex].count) {
            toMove = items[slotIndex].getMaxStackSize() - items[slotIndex].count;
        }
        if (toMove > maxStackSize - items[slotIndex].count) {
            toMove = maxStackSize - items[slotIndex].count;
        }
        if (toMove == 0) {
            return remaining;
        }

        remaining -= toMove;
        items[slotIndex].count += toMove;
        items[slotIndex].b = 5;
        return remaining;
    }

    public ItemStack splitCombined(ItemStack[] items, ItemStack[] armor, int index, int amount) {
        ItemStack[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= items.length) {
            target = armor;
            resolvedIndex -= items.length;
        }

        if (target[resolvedIndex] == null) {
            return null;
        }

        if (target[resolvedIndex].count <= amount) {
            ItemStack extracted = target[resolvedIndex];
            target[resolvedIndex] = null;
            return extracted;
        }

        ItemStack extracted = target[resolvedIndex].a(amount);
        if (target[resolvedIndex].count == 0) {
            target[resolvedIndex] = null;
        }
        return extracted;
    }

    public void setCombined(ItemStack[] items, ItemStack[] armor, int index, ItemStack itemStack) {
        ItemStack[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= target.length) {
            resolvedIndex -= target.length;
            target = armor;
        }
        target[resolvedIndex] = itemStack;
    }

    public int combinedSize(ItemStack[] items, ItemStack[] armor) {
        return items.length + armor.length;
    }

    public ItemStack getCombined(ItemStack[] items, ItemStack[] armor, int index) {
        ItemStack[] target = items;
        int resolvedIndex = index;
        if (resolvedIndex >= target.length) {
            resolvedIndex -= target.length;
            target = armor;
        }
        return target[resolvedIndex];
    }
}
