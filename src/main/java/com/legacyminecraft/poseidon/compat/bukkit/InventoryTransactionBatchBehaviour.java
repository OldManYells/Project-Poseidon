package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Canonical behaviour for CraftInventory add/remove batch transaction orchestration.
 */
public final class InventoryTransactionBatchBehaviour {
    private static final InventoryTransactionBatchBehaviour INSTANCE = new InventoryTransactionBatchBehaviour();

    private InventoryTransactionBatchBehaviour() {
    }

    public static InventoryTransactionBatchBehaviour getInstance() {
        return INSTANCE;
    }

    public HashMap<Integer, ItemStack> processAdds(ItemStack[] items, TransactionGate transactionGate, AddMutation addMutation) {
        HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        for (int index = 0; index < items.length; index++) {
            ItemStack item = items[index];
            if (transactionGate.isCancelled(item)) {
                continue;
            }

            if (!addMutation.tryAdd(item)) {
                leftover.put(index, item);
            }
        }
        return leftover;
    }

    public HashMap<Integer, ItemStack> processRemovals(ItemStack[] items, TransactionGate transactionGate, RemoveMutation removeMutation) {
        HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        for (int index = 0; index < items.length; index++) {
            ItemStack item = items[index];
            if (transactionGate.isCancelled(item)) {
                continue;
            }

            int remaining = removeMutation.remove(item);
            if (remaining > 0) {
                item.setAmount(remaining);
                leftover.put(index, item);
            }
        }
        return leftover;
    }

    public interface TransactionGate {
        boolean isCancelled(ItemStack item);
    }

    public interface AddMutation {
        boolean tryAdd(ItemStack item);
    }

    public interface RemoveMutation {
        int remove(ItemStack item);
    }
}
