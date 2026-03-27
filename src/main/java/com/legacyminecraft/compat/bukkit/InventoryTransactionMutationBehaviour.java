package com.legacyminecraft.compat.bukkit;


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

    public boolean addItem(InventoryAccess access, Object item) {
        while (true) {
            int firstPartial = access.firstPartial(item);

            if (firstPartial == -1) {
                int firstFree = access.firstEmpty();
                if (firstFree == -1) {
                    return false;
                }

                int itemAmount = ((Number) BridgeReflection.invoke(item, "getAmount")).intValue();
                if (itemAmount > access.getMaxItemStack()) {
                    int typeId = ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue();
                    short durability = ((Number) BridgeReflection.invoke(item, "getDurability")).shortValue();
                    access.setItem(firstFree, access.createItem(typeId, access.getMaxItemStack(), durability));
                    BridgeReflection.invoke(item, "setAmount", itemAmount - access.getMaxItemStack());
                } else {
                    access.setItem(firstFree, item);
                    return true;
                }
            } else {
                Object partialItem = access.getItem(firstPartial);
                int amount = ((Number) BridgeReflection.invoke(item, "getAmount")).intValue();
                int partialAmount = ((Number) BridgeReflection.invoke(partialItem, "getAmount")).intValue();
                int maxAmount = ((Number) BridgeReflection.invoke(partialItem, "getMaxStackSize")).intValue();

                if (amount + partialAmount <= maxAmount) {
                    BridgeReflection.invoke(partialItem, "setAmount", amount + partialAmount);
                    return true;
                }

                BridgeReflection.invoke(partialItem, "setAmount", maxAmount);
                BridgeReflection.invoke(item, "setAmount", amount + partialAmount - maxAmount);
            }
        }
    }

    public int removeItem(InventoryAccess access, Object item) {
        int toDelete = ((Number) BridgeReflection.invoke(item, "getAmount")).intValue();

        while (true) {
            Object material = BridgeReflection.invoke(item, "getType");
            int first = access.first(material);
            if (first == -1) {
                return toDelete;
            }

            Object itemStack = access.getItem(first);
            int amount = ((Number) BridgeReflection.invoke(itemStack, "getAmount")).intValue();

            if (amount <= toDelete) {
                toDelete -= amount;
                access.clear(first);
            } else {
                BridgeReflection.invoke(itemStack, "setAmount", amount - toDelete);
                access.setItem(first, itemStack);
                toDelete = 0;
            }

            if (toDelete <= 0) {
                return 0;
            }
        }
    }

    public interface InventoryAccess {
        int firstPartial(Object item);

        int firstEmpty();

        int getMaxItemStack();

        void setItem(int index, Object item);

        Object getItem(int index);

        int first(Object material);

        void clear(int index);

        Object createItem(int typeId, int amount, short durability);
    }
}
