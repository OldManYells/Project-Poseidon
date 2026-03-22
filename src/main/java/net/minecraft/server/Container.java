package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ContainerInteractionBehaviour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Container {

    public List d = new ArrayList();
    public List e = new ArrayList();
    public int windowId = 0;
    private short a = 0;
    protected List listeners = new ArrayList();
    private Set b = new HashSet();
    private final ContainerInteractionBehaviour interactionService = ContainerInteractionBehaviour.getInstance();

    public Container() {}

    protected void a(Slot slot) {
        interactionService.addSlot(this.e, this.d, slot);
    }

    public final void poseidonAddSlot(Slot slot) {
        this.a(slot);
    }

    public void a(ICrafting icrafting) {
        interactionService.addListener(this, this.listeners, this.e, this.d, icrafting);
    }

    public List b() {
        return interactionService.collectSlotItems(this.e);
    }

    public void a() {
        interactionService.broadcastChanges(this, this.e, this.d, this.listeners);
    }

    public Slot a(IInventory iinventory, int i) {
        return interactionService.findSlot(this.e, iinventory, i);
    }

    public Slot b(int i) {
        return interactionService.getSlot(this.e, i);
    }

    public ItemStack a(int i) {
        return interactionService.getSlotItem(this.e, i);
    }

    public ItemStack a(int i, int j, boolean flag, EntityHuman entityhuman) {
        return interactionService.clickSlot(this, this.e, i, j, flag, entityhuman);
    }

    public void a(EntityHuman entityhuman) {
        interactionService.dropCarriedItem(entityhuman);
    }

    public void a(IInventory iinventory) {
        this.a();
    }

    public boolean c(EntityHuman entityhuman) {
        return interactionService.canInteract(this.b, entityhuman);
    }

    public void a(EntityHuman entityhuman, boolean flag) {
        interactionService.updateInteractionState(this.b, entityhuman, flag);
    }

    public abstract boolean b(EntityHuman entityhuman);

    protected void a(ItemStack itemstack, int i, int j, boolean flag) {
        interactionService.mergeItemStack(this.e, itemstack, i, j, flag);
    }

    public final void poseidonMergeItemStack(ItemStack itemstack, int i, int j, boolean flag) {
        this.a(itemstack, i, j, flag);
    }
}
