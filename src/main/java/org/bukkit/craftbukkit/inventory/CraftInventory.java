package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.compat.bukkit.InventoryItemBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.InventoryRemovalBehaviour;
import com.legacyminecraft.compat.bukkit.InventorySearchBehaviour;
import com.legacyminecraft.compat.bukkit.InventoryTransactionBatchBehaviour;
import com.legacyminecraft.compat.bukkit.InventoryTransactionEventBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.InventoryTransactionMutationBehaviour;
import net.minecraft.server.IInventory;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryTransactionType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CraftInventory implements org.bukkit.inventory.Inventory {
    private static final InventoryItemBridgeBehaviour INVENTORY_ITEM_BRIDGE_BEHAVIOUR =
            InventoryItemBridgeBehaviour.getInstance();
    private static final InventorySearchBehaviour INVENTORY_SEARCH_BEHAVIOUR =
            InventorySearchBehaviour.getInstance();
    private static final InventoryRemovalBehaviour INVENTORY_REMOVAL_BEHAVIOUR =
            InventoryRemovalBehaviour.getInstance();
    private static final InventoryTransactionBatchBehaviour INVENTORY_TRANSACTION_BATCH_BEHAVIOUR =
            InventoryTransactionBatchBehaviour.getInstance();
    private static final InventoryTransactionEventBridgeBehaviour INVENTORY_TRANSACTION_EVENT_BRIDGE_BEHAVIOUR =
            InventoryTransactionEventBridgeBehaviour.getInstance();
    private static final InventoryTransactionMutationBehaviour INVENTORY_TRANSACTION_MUTATION_BEHAVIOUR =
            InventoryTransactionMutationBehaviour.getInstance();
    protected IInventory inventory;

    public CraftInventory(IInventory inventory) {
        this.inventory = inventory;
    }

    public IInventory getInventory() {
        return inventory;
    }

    public int getSize() {
        return getInventory().getSize();
    }

    public String getName() {
        return getInventory().getName();
    }

    public ItemStack getItem(int index) {
        return INVENTORY_ITEM_BRIDGE_BEHAVIOUR.wrapSingle(getInventory().getItem(index));
    }

    public ItemStack[] getContents() {
        net.minecraft.server.ItemStack[] mcItems = getInventory().getContents();
        Object[] projected = INVENTORY_ITEM_BRIDGE_BEHAVIOUR.toBukkitContents(mcItems, getSize());
        ItemStack[] items = new ItemStack[projected.length];
        for (int i = 0; i < projected.length; i++) {
            items[i] = (ItemStack) projected[i];
        }
        return items;
    }

    public void setContents(ItemStack[] items) {
        if (getInventory().getContents().length != items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length + " and got " + items.length); // Poseidon
        }

        net.minecraft.server.ItemStack[] mcItems = getInventory().getContents();
        INVENTORY_ITEM_BRIDGE_BEHAVIOUR.copyContentsToNms(items, mcItems);
    }

    public void setItem(int index, ItemStack item) {
        getInventory().setItem(index, (net.minecraft.server.ItemStack) INVENTORY_ITEM_BRIDGE_BEHAVIOUR.toNmsForSetItem(item));
    }

    public boolean contains(int materialId) {
        return INVENTORY_SEARCH_BEHAVIOUR.containsMaterialId(getContents(), materialId);
    }

    public boolean contains(Material material) {
        return contains(material.getId());
    }

    public boolean contains(ItemStack item) {
        return INVENTORY_SEARCH_BEHAVIOUR.containsItem(getContents(), item);
    }

    public boolean contains(int materialId, int amount) {
        return INVENTORY_SEARCH_BEHAVIOUR.containsMaterialId(getContents(), materialId, amount);
    }

    public boolean contains(Material material, int amount) {
        return contains(material.getId(), amount);
    }

    public boolean contains(ItemStack item, int amount) {
        return INVENTORY_SEARCH_BEHAVIOUR.containsItem(getContents(), item, amount);
    }

    public HashMap<Integer, ItemStack> all(int materialId) {
        return castItemMap(INVENTORY_SEARCH_BEHAVIOUR.allByMaterialId(getContents(), materialId));
    }

    public HashMap<Integer, ItemStack> all(Material material) {
        return all(material.getId());
    }

    public HashMap<Integer, ItemStack> all(ItemStack item) {
        return castItemMap(INVENTORY_SEARCH_BEHAVIOUR.allByItem(getContents(), item));
    }

    public int first(int materialId) {
        return INVENTORY_SEARCH_BEHAVIOUR.firstByMaterialId(getContents(), materialId);
    }

    public int first(Material material) {
        return first(material.getId());
    }

    public int first(ItemStack item) {
        return INVENTORY_SEARCH_BEHAVIOUR.firstByItem(getContents(), item);
    }

    public int firstEmpty() {
        return INVENTORY_SEARCH_BEHAVIOUR.firstEmpty(getContents());
    }

    public int firstPartial(int materialId) {
        return INVENTORY_SEARCH_BEHAVIOUR.firstPartialByMaterialId(getContents(), materialId);
    }

    public int firstPartial(Material material) {
        return firstPartial(material.getId());
    }

    public int firstPartial(ItemStack item) {
        return INVENTORY_SEARCH_BEHAVIOUR.firstPartialByItem(getContents(), item);
    }

    public HashMap<Integer, ItemStack> addItem(ItemStack... items) {
        return castItemMap(INVENTORY_TRANSACTION_BATCH_BEHAVIOUR.processAdds(
                items,
                new InventoryTransactionBatchBehaviour.TransactionGate() {
                    public boolean isCancelled(Object item) {
                        return INVENTORY_TRANSACTION_EVENT_BRIDGE_BEHAVIOUR.isCancelled(
                                InventoryTransactionType.ITEM_ADDED,
                                CraftInventory.this,
                                item
                        );
                    }
                },
                new InventoryTransactionBatchBehaviour.AddMutation() {
                    public boolean tryAdd(Object item) {
                        return INVENTORY_TRANSACTION_MUTATION_BEHAVIOUR.addItem(createMutationAccess(), item);
                    }
                }
        ));
    }

    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) {
        return castItemMap(INVENTORY_TRANSACTION_BATCH_BEHAVIOUR.processRemovals(
                items,
                new InventoryTransactionBatchBehaviour.TransactionGate() {
                    public boolean isCancelled(Object item) {
                        return INVENTORY_TRANSACTION_EVENT_BRIDGE_BEHAVIOUR.isCancelled(
                                InventoryTransactionType.ITEM_REMOVED,
                                CraftInventory.this,
                                item
                        );
                    }
                },
                new InventoryTransactionBatchBehaviour.RemoveMutation() {
                    public int remove(Object item) {
                        return INVENTORY_TRANSACTION_MUTATION_BEHAVIOUR.removeItem(createMutationAccess(), item);
                    }
                }
        ));
    }

    private int getMaxItemStack() {
        return getInventory().getMaxStackSize();
    }

    private InventoryTransactionMutationBehaviour.InventoryAccess createMutationAccess() {
        return new InventoryTransactionMutationBehaviour.InventoryAccess() {
            public int firstPartial(Object item) {
                return CraftInventory.this.firstPartial((ItemStack) item);
            }

            public int firstEmpty() {
                return CraftInventory.this.firstEmpty();
            }

            public int getMaxItemStack() {
                return CraftInventory.this.getMaxItemStack();
            }

            public void setItem(int index, Object item) {
                CraftInventory.this.setItem(index, (ItemStack) item);
            }

            public Object getItem(int index) {
                return CraftInventory.this.getItem(index);
            }

            public int first(Object material) {
                return CraftInventory.this.first((Material) material);
            }

            public void clear(int index) {
                CraftInventory.this.clear(index);
            }

            public Object createItem(int typeId, int amount, short durability) {
                return new CraftItemStack(typeId, amount, durability);
            }
        };
    }

    public void remove(int materialId) {
        INVENTORY_REMOVAL_BEHAVIOUR.removeMaterialId(
                getContents(),
                materialId,
                new InventoryRemovalBehaviour.SlotClearer() {
                    public void clear(int index) {
                        CraftInventory.this.clear(index);
                    }
                }
        );
    }

    public void remove(Material material) {
        remove(material.getId());
    }

    public void remove(ItemStack item) {
        INVENTORY_REMOVAL_BEHAVIOUR.removeMatchingItem(
                getContents(),
                item,
                new InventoryRemovalBehaviour.SlotClearer() {
                    public void clear(int index) {
                        CraftInventory.this.clear(index);
                    }
                }
        );
    }

    public void clear(int index) {
        setItem(index, null);
    }

    public void clear() {
        INVENTORY_REMOVAL_BEHAVIOUR.clearAll(
                getSize(),
                new InventoryRemovalBehaviour.SlotClearer() {
                    public void clear(int index) {
                        CraftInventory.this.clear(index);
                    }
                }
        );
    }

    private HashMap<Integer, ItemStack> castItemMap(HashMap<Integer, Object> source) {
        HashMap<Integer, ItemStack> cast = new HashMap<Integer, ItemStack>();
        for (Integer key : source.keySet()) {
            cast.put(key, (ItemStack) source.get(key));
        }
        return cast;
    }
}
