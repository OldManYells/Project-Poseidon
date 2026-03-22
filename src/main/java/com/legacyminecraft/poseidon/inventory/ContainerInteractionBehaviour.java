package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Canonical inventory/container interaction logic extracted from legacy NMS wrappers.
 */
public final class ContainerInteractionBehaviour {
    private static final ContainerInteractionBehaviour INSTANCE = new ContainerInteractionBehaviour();

    private ContainerInteractionBehaviour() {
    }

    public static ContainerInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public void addSlot(List slots, List cachedItems, Slot slot) {
        slot.a = slots.size();
        slots.add(slot);
        cachedItems.add(null);
    }

    public void addListener(Container container, List listeners, List slots, List cachedItems, ICrafting listener) {
        if (listeners.contains(listener)) {
            throw new IllegalArgumentException("Listener already listening");
        }

        listeners.add(listener);
        listener.a(container, collectSlotItems(slots));
        broadcastChanges(container, slots, cachedItems, listeners);
    }

    public List collectSlotItems(List slots) {
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < slots.size(); ++i) {
            arraylist.add(((Slot) slots.get(i)).getItem());
        }

        return arraylist;
    }

    public void broadcastChanges(Container container, List slots, List cachedItems, List listeners) {
        for (int i = 0; i < slots.size(); ++i) {
            ItemStack itemstack = ((Slot) slots.get(i)).getItem();
            ItemStack itemstack1 = (ItemStack) cachedItems.get(i);

            if (!ItemStack.equals(itemstack1, itemstack)) {
                itemstack1 = itemstack == null ? null : itemstack.cloneItemStack();
                cachedItems.set(i, itemstack1);

                for (int j = 0; j < listeners.size(); ++j) {
                    ((ICrafting) listeners.get(j)).a(container, i, itemstack1);
                }
            }
        }
    }

    public Slot findSlot(List slots, IInventory inventory, int slotIndex) {
        for (int j = 0; j < slots.size(); ++j) {
            Slot slot = (Slot) slots.get(j);

            if (slot.a(inventory, slotIndex)) {
                return slot;
            }
        }

        return null;
    }

    public Slot getSlot(List slots, int index) {
        return (Slot) slots.get(index);
    }

    public ItemStack getSlotItem(List slots, int index) {
        Slot slot = (Slot) slots.get(index);
        return slot != null ? slot.getItem() : null;
    }

    public ItemStack clickSlot(Container container, List slots, int i, int j, boolean flag, EntityHuman entityhuman) {
        ItemStack itemstack = null;

        if (j == 0 || j == 1) {
            InventoryPlayer inventoryplayer = entityhuman.inventory;

            if (i == -999) {
                if (inventoryplayer.j() != null && i == -999) {
                    if (j == 0) {
                        entityhuman.b(inventoryplayer.j());
                        inventoryplayer.b((ItemStack) null);
                    }

                    if (j == 1) {
                        entityhuman.b(inventoryplayer.j().a(1));
                        if (inventoryplayer.j().count == 0) {
                            inventoryplayer.b((ItemStack) null);
                        }
                    }
                }
            } else {
                int k;

                if (flag) {
                    ItemStack itemstack1 = container.a(i);

                    if (itemstack1 != null) {
                        int l = itemstack1.count;

                        itemstack = itemstack1.cloneItemStack();
                        Slot slot = (Slot) slots.get(i);

                        if (slot != null && slot.getItem() != null) {
                            k = slot.getItem().count;
                            if (k < l) {
                                container.a(i, j, true, entityhuman);
                            }
                        }
                    }
                } else {
                    Slot slot1 = (Slot) slots.get(i);

                    if (slot1 != null) {
                        slot1.c();
                        ItemStack itemstack2 = slot1.getItem();
                        ItemStack itemstack3 = inventoryplayer.j();

                        if (itemstack2 != null) {
                            itemstack = itemstack2.cloneItemStack();
                        }

                        if (itemstack2 == null) {
                            if (itemstack3 != null && slot1.isAllowed(itemstack3)) {
                                k = j == 0 ? itemstack3.count : 1;
                                if (k > slot1.d()) {
                                    k = slot1.d();
                                }

                                slot1.c(itemstack3.a(k));
                                if (itemstack3.count == 0) {
                                    inventoryplayer.b((ItemStack) null);
                                }
                            }
                        } else if (itemstack3 == null) {
                            k = j == 0 ? itemstack2.count : (itemstack2.count + 1) / 2;
                            ItemStack itemstack4 = slot1.a(k);

                            inventoryplayer.b(itemstack4);
                            if (itemstack2.count == 0) {
                                slot1.c((ItemStack) null);
                            }

                            slot1.a(inventoryplayer.j());
                        } else if (slot1.isAllowed(itemstack3)) {
                            if (itemstack2.id == itemstack3.id && (!itemstack2.usesData() || itemstack2.getData() == itemstack3.getData())) {
                                k = j == 0 ? itemstack3.count : 1;
                                if (k > slot1.d() - itemstack2.count) {
                                    k = slot1.d() - itemstack2.count;
                                }

                                if (k > itemstack3.getMaxStackSize() - itemstack2.count) {
                                    k = itemstack3.getMaxStackSize() - itemstack2.count;
                                }

                                itemstack3.a(k);
                                if (itemstack3.count == 0) {
                                    inventoryplayer.b((ItemStack) null);
                                }

                                itemstack2.count += k;
                            } else if (itemstack3.count <= slot1.d()) {
                                slot1.c(itemstack3);
                                inventoryplayer.b(itemstack2);
                            }
                        } else if (itemstack2.id == itemstack3.id && itemstack3.getMaxStackSize() > 1 && (!itemstack2.usesData() || itemstack2.getData() == itemstack3.getData())) {
                            k = itemstack2.count;
                            if (k > 0 && k + itemstack3.count <= itemstack3.getMaxStackSize()) {
                                itemstack3.count += k;
                                itemstack2.a(k);
                                if (itemstack2.count == 0) {
                                    slot1.c((ItemStack) null);
                                }

                                slot1.a(inventoryplayer.j());
                            }
                        }
                    }
                }
            }
        }

        return itemstack;
    }

    public void dropCarriedItem(EntityHuman entityhuman) {
        InventoryPlayer inventoryplayer = entityhuman.inventory;

        if (inventoryplayer.j() != null) {
            entityhuman.b(inventoryplayer.j());
            inventoryplayer.b((ItemStack) null);
        }
    }

    public boolean canInteract(Set blockedPlayers, EntityHuman entityhuman) {
        return !blockedPlayers.contains(entityhuman);
    }

    public void updateInteractionState(Set blockedPlayers, EntityHuman entityhuman, boolean flag) {
        if (flag) {
            blockedPlayers.remove(entityhuman);
        } else {
            blockedPlayers.add(entityhuman);
        }
    }

    public void mergeItemStack(List slots, ItemStack itemstack, int i, int j, boolean flag) {
        int k = i;

        if (flag) {
            k = j - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (itemstack.isStackable()) {
            while (itemstack.count > 0 && (!flag && k < j || flag && k >= i)) {
                slot = (Slot) slots.get(k);
                itemstack1 = slot.getItem();
                if (itemstack1 != null && itemstack1.id == itemstack.id && (!itemstack.usesData() || itemstack.getData() == itemstack1.getData())) {
                    int l = itemstack1.count + itemstack.count;

                    if (l <= itemstack.getMaxStackSize()) {
                        itemstack.count = 0;
                        itemstack1.count = l;
                        slot.c();
                    } else if (itemstack1.count < itemstack.getMaxStackSize()) {
                        itemstack.count -= itemstack.getMaxStackSize() - itemstack1.count;
                        itemstack1.count = itemstack.getMaxStackSize();
                        slot.c();
                    }
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (itemstack.count > 0) {
            if (flag) {
                k = j - 1;
            } else {
                k = i;
            }

            while (!flag && k < j || flag && k >= i) {
                slot = (Slot) slots.get(k);
                itemstack1 = slot.getItem();
                if (itemstack1 == null) {
                    slot.c(itemstack.cloneItemStack());
                    slot.c();
                    itemstack.count = 0;
                    break;
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }
    }
}
