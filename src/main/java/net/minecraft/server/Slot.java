package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.SlotInteractionBehaviour;

public class Slot {
    private static final SlotInteractionBehaviour SLOT_INTERACTION_BEHAVIOUR = SlotInteractionBehaviour.getInstance();

    public final int index; // CraftBukkit - private -> public
    public final IInventory inventory; // CraftBukkit - private -> public
    public int a;
    public int b;
    public int c;

    public Slot(IInventory iinventory, int i, int j, int k) {
        this.inventory = iinventory;
        this.index = i;
        this.b = j;
        this.c = k;
    }

    public void a(ItemStack itemstack) {
        SLOT_INTERACTION_BEHAVIOUR.onSet();
        this.c();
    }

    public boolean isAllowed(ItemStack itemstack) {
        return SLOT_INTERACTION_BEHAVIOUR.isAllowed(itemstack);
    }

    public ItemStack getItem() {
        return (ItemStack) (Object) SLOT_INTERACTION_BEHAVIOUR.getItem(this.inventory, this.index);
    }

    public boolean b() {
        return SLOT_INTERACTION_BEHAVIOUR.hasItem(this.getItem());
    }

    public void c(ItemStack itemstack) {
        SLOT_INTERACTION_BEHAVIOUR.setItem(this.inventory, this.index, itemstack);
        this.c();
    }

    public void c() {
        SLOT_INTERACTION_BEHAVIOUR.onInventoryChanged(this.inventory);
    }

    public int d() {
        return SLOT_INTERACTION_BEHAVIOUR.getMaxStackSize(this.inventory);
    }

    public ItemStack a(int i) {
        return (ItemStack) (Object) SLOT_INTERACTION_BEHAVIOUR.splitStack(this.inventory, this.index, i);
    }

    public boolean a(IInventory iinventory, int i) {
        return SLOT_INTERACTION_BEHAVIOUR.matchesInventorySlot(this.inventory, this.index, iinventory, i);
    }
}
