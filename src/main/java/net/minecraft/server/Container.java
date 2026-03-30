package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Container {

    // Cached stacks sent to listeners
    public List d = new ArrayList();

    // Slots in this container
    public List e = new ArrayList();

    public int windowId = 0;

    // Better name: transactionId
    private short a = 0;

    protected List listeners = new ArrayList();

    // Players temporarily blocked from crafting/interacting
    private Set b = new HashSet();

    public Container() {}

    protected void addSlot(Slot slot) {
        slot.a = this.e.size();
        this.e.add(slot);
        this.d.add(null);
    }

    public void addSlotListener(ICrafting listener) {
        if (this.listeners.contains(listener)) {
            throw new IllegalArgumentException("Listener already listening");
        }

        this.listeners.add(listener);
        listener.a(this, this.getContents());
        this.detectAndSendChanges();
    }

    public List getContents() {
        ArrayList contents = new ArrayList();

        for (int i = 0; i < this.e.size(); ++i) {
            contents.add(((Slot) this.e.get(i)).getItem());
        }

        return contents;
    }

    public void detectAndSendChanges() {
        for (int i = 0; i < this.e.size(); ++i) {
            ItemStack current = ((Slot) this.e.get(i)).getItem();
            ItemStack cached = (ItemStack) this.d.get(i);

            if (!ItemStack.equals(cached, current)) {
                cached = current == null ? null : current.cloneItemStack();
                this.d.set(i, cached);

                for (int listenerIndex = 0; listenerIndex < this.listeners.size(); ++listenerIndex) {
                    ((ICrafting) this.listeners.get(listenerIndex)).a(this, i, cached);
                }
            }
        }
    }

    public Slot getSlot(IInventory inventory, int slotIndex) {
        for (int i = 0; i < this.e.size(); ++i) {
            Slot slot = (Slot) this.e.get(i);

            if (slot.a(inventory, slotIndex)) {
                return slot;
            }
        }

        return null;
    }

    public Slot getSlot(int slotIndex) {
        return (Slot) this.e.get(slotIndex);
    }

    public ItemStack getItem(int slotIndex) {
        Slot slot = (Slot) this.e.get(slotIndex);
        return slot != null ? slot.getItem() : null;
    }

    public ItemStack clickItem(int slotIndex, int mouseButton, boolean shiftHeld, EntityHuman player) {
        ItemStack result = null;

        if (mouseButton == 0 || mouseButton == 1) {
            InventoryPlayer playerInventory = player.inventory;

            if (slotIndex == -999) {
                if (playerInventory.j() != null) {
                    if (mouseButton == 0) {
                        player.b(playerInventory.j());
                        playerInventory.b((ItemStack) null);
                    }

                    if (mouseButton == 1) {
                        player.b(playerInventory.j().splitStack(1));
                        if (playerInventory.j().count == 0) {
                            playerInventory.b((ItemStack) null);
                        }
                    }
                }
            } else {
                int amount;

                if (shiftHeld) {
                    ItemStack clicked = this.getItem(slotIndex);

                    if (clicked != null) {
                        int previousCount = clicked.count;
                        result = clicked.cloneItemStack();
                        Slot slot = (Slot) this.e.get(slotIndex);

                        if (slot != null && slot.getItem() != null) {
                            amount = slot.getItem().count;
                            if (amount < previousCount) {
                                this.clickItem(slotIndex, mouseButton, shiftHeld, player);
                            }
                        }
                    }
                } else {
                    Slot slot = (Slot) this.e.get(slotIndex);

                    if (slot != null) {
                        slot.c();
                        ItemStack slotStack = slot.getItem();
                        ItemStack carriedStack = playerInventory.j();

                        if (slotStack != null) {
                            result = slotStack.cloneItemStack();
                        }

                        if (slotStack == null) {
                            if (carriedStack != null && slot.isAllowed(carriedStack)) {
                                amount = mouseButton == 0 ? carriedStack.count : 1;
                                if (amount > slot.d()) {
                                    amount = slot.d();
                                }

                                slot.c(carriedStack.splitStack(amount));
                                if (carriedStack.count == 0) {
                                    playerInventory.b((ItemStack) null);
                                }
                            }
                        } else if (carriedStack == null) {
                            amount = mouseButton == 0 ? slotStack.count : (slotStack.count + 1) / 2;
                            ItemStack taken = slot.getItem().splitStack(amount);

                            playerInventory.b(taken);
                            if (slotStack.count == 0) {
                                slot.c((ItemStack) null);
                            }

                            slot.a(playerInventory.j());
                        } else if (slot.isAllowed(carriedStack)) {
                            if (slotStack.id == carriedStack.id
                                    && (!slotStack.usesData() || slotStack.getData() == carriedStack.getData())) {

                                amount = mouseButton == 0 ? carriedStack.count : 1;
                                if (amount > slot.d() - slotStack.count) {
                                    amount = slot.d() - slotStack.count;
                                }

                                if (amount > carriedStack.getMaxStackSize() - slotStack.count) {
                                    amount = carriedStack.getMaxStackSize() - slotStack.count;
                                }

                                carriedStack.splitStack(amount);
                                if (carriedStack.count == 0) {
                                    playerInventory.b((ItemStack) null);
                                }

                                slotStack.count += amount;
                            } else if (carriedStack.count <= slot.d()) {
                                slot.c(carriedStack);
                                playerInventory.b(slotStack);
                            }
                        } else if (slotStack.id == carriedStack.id
                                && carriedStack.getMaxStackSize() > 1
                                && (!slotStack.usesData() || slotStack.getData() == carriedStack.getData())) {

                            amount = slotStack.count;
                            if (amount > 0 && amount + carriedStack.count <= carriedStack.getMaxStackSize()) {
                                carriedStack.count += amount;
                                slotStack.splitStack(amount);
                                if (slotStack.count == 0) {
                                    slot.c((ItemStack) null);
                                }

                                slot.a(playerInventory.j());
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public void onContainerClosed(EntityHuman player) {
        InventoryPlayer playerInventory = player.inventory;

        if (playerInventory.j() != null) {
            player.b(playerInventory.j());
            playerInventory.b((ItemStack) null);
        }
    }

    public void onInventoryChanged(IInventory inventory) {
        this.detectAndSendChanges();
    }

    public boolean canCraft(EntityHuman player) {
        return !this.b.contains(player);
    }

    public void setCanCraft(EntityHuman player, boolean canCraft) {
        if (canCraft) {
            this.b.remove(player);
        } else {
            this.b.add(player);
        }
    }

    public boolean canUse(EntityHuman player) {
        return this.b(player);
    }

    protected void mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverse) {
        int slotIndex = startIndex;

        if (reverse) {
            slotIndex = endIndex - 1;
        }

        Slot slot;
        ItemStack slotStack;

        if (stack.isStackable()) {
            while (stack.count > 0 && (!reverse && slotIndex < endIndex || reverse && slotIndex >= startIndex)) {
                slot = (Slot) this.e.get(slotIndex);
                slotStack = slot.getItem();

                if (slotStack != null
                        && slotStack.id == stack.id
                        && (!stack.usesData() || stack.getData() == slotStack.getData())) {

                    int mergedCount = slotStack.count + stack.count;

                    if (mergedCount <= stack.getMaxStackSize()) {
                        stack.count = 0;
                        slotStack.count = mergedCount;
                        slot.c();
                    } else if (slotStack.count < stack.getMaxStackSize()) {
                        stack.count -= stack.getMaxStackSize() - slotStack.count;
                        slotStack.count = stack.getMaxStackSize();
                        slot.c();
                    }
                }

                if (reverse) {
                    --slotIndex;
                } else {
                    ++slotIndex;
                }
            }
        }

        if (stack.count > 0) {
            slotIndex = reverse ? endIndex - 1 : startIndex;

            while (!reverse && slotIndex < endIndex || reverse && slotIndex >= startIndex) {
                slot = (Slot) this.e.get(slotIndex);
                slotStack = slot.getItem();

                if (slotStack == null) {
                    slot.c(stack.cloneItemStack());
                    slot.c();
                    stack.count = 0;
                    break;
                }

                if (reverse) {
                    --slotIndex;
                } else {
                    ++slotIndex;
                }
            }
        }
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    protected void a(Slot slot) {
        this.addSlot(slot);
    }

    public void a(ICrafting listener) {
        this.addSlotListener(listener);
    }

    public List b() {
        return this.getContents();
    }

    public void a() {
        this.detectAndSendChanges();
    }

    public Slot a(IInventory inventory, int slotIndex) {
        return this.getSlot(inventory, slotIndex);
    }

    public Slot b(int slotIndex) {
        return this.getSlot(slotIndex);
    }

    public ItemStack a(int slotIndex) {
        return this.getItem(slotIndex);
    }

    public ItemStack a(int slotIndex, int mouseButton, boolean shiftHeld, EntityHuman player) {
        return this.clickItem(slotIndex, mouseButton, shiftHeld, player);
    }

    public void a(EntityHuman player) {
        this.onContainerClosed(player);
    }

    public void a(IInventory inventory) {
        this.onInventoryChanged(inventory);
    }

    public boolean c(EntityHuman player) {
        return this.canCraft(player);
    }

    public void a(EntityHuman player, boolean canCraft) {
        this.setCanCraft(player, canCraft);
    }

    public abstract boolean b(EntityHuman player);

    protected void a(ItemStack stack, int startIndex, int endIndex, boolean reverse) {
        this.mergeItemStack(stack, startIndex, endIndex, reverse);
    }
}