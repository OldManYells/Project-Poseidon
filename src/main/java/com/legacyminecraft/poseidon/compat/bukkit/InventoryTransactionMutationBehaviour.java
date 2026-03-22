package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.ItemStack;

/**
 * Canonical behaviour for CraftInventory add/remove mutation loops (event dispatch remains in wrappers).
 */
public final class InventoryTransactionMutationBehaviour {
    private static final InventoryTransactionMutationBehaviour INSTANCE = new InventoryTransactionMutationBehaviour();

    private InventoryTransactionMutationBehaviour() {
    }

    public static InventoryTransactionMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean addItem(InventoryAccess access, ItemStack item) {
        while (true) {
            int firstPartial = access.firstPartial(item);

            if (firstPartial == -1) {
                int firstFree = access.firstEmpty();
                if (firstFree == -1) {
                    return false;
                }

                if (item.getAmount() > access.getMaxItemStack()) {
                    access.setItem(firstFree, access.createItem(item.getTypeId(), access.getMaxItemStack(), item.getDurability()));
                    item.setAmount(item.getAmount() - access.getMaxItemStack());
                } else {
                    access.setItem(firstFree, item);
                    return true;
                }
            } else {
                ItemStack partialItem = access.getItem(firstPartial);
                int amount = item.getAmount();
                int partialAmount = partialItem.getAmount();
                int maxAmount = partialItem.getMaxStackSize();

                if (amount + partialAmount <= maxAmount) {
                    partialItem.setAmount(amount + partialAmount);
                    return true;
                }

                partialItem.setAmount(maxAmount);
                item.setAmount(amount + partialAmount - maxAmount);
            }
        }
    }

    public int removeItem(InventoryAccess access, ItemStack item) {
        int toDelete = item.getAmount();

        while (true) {
            int first = access.first(item.getType());
            if (first == -1) {
                return toDelete;
            }

            ItemStack itemStack = access.getItem(first);
            int amount = itemStack.getAmount();

            if (amount <= toDelete) {
                toDelete -= amount;
                access.clear(first);
            } else {
                itemStack.setAmount(amount - toDelete);
                access.setItem(first, itemStack);
                toDelete = 0;
            }

            if (toDelete <= 0) {
                return 0;
            }
        }
    }

    public interface InventoryAccess {
        int firstPartial(ItemStack item);

        int firstEmpty();

        int getMaxItemStack();

        void setItem(int index, ItemStack item);

        ItemStack getItem(int index);

        int first(org.bukkit.Material material);

        void clear(int index);

        ItemStack createItem(int typeId, int amount, short durability);
    }
}
