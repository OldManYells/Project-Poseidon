package com.legacyminecraft.compat.bukkit;


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

    public HashMap<Integer, Object> processAdds(Object[] items, TransactionGate transactionGate, AddMutation addMutation) {
        HashMap<Integer, Object> leftover = new HashMap<Integer, Object>();
        for (int index = 0; index < items.length; index++) {
            Object item = items[index];
            if (transactionGate.isCancelled(item)) {
                continue;
            }

            if (!addMutation.tryAdd(item)) {
                leftover.put(index, item);
            }
        }
        return leftover;
    }

    public HashMap<Integer, Object> processRemovals(Object[] items, TransactionGate transactionGate, RemoveMutation removeMutation) {
        HashMap<Integer, Object> leftover = new HashMap<Integer, Object>();
        for (int index = 0; index < items.length; index++) {
            Object item = items[index];
            if (transactionGate.isCancelled(item)) {
                continue;
            }

            int remaining = removeMutation.remove(item);
            if (remaining > 0) {
                BridgeReflection.invoke(item, "setAmount", remaining);
                leftover.put(index, item);
            }
        }
        return leftover;
    }

    public interface TransactionGate {
        boolean isCancelled(Object item);
    }

    public interface AddMutation {
        boolean tryAdd(Object item);
    }

    public interface RemoveMutation {
        int remove(Object item);
    }
}
