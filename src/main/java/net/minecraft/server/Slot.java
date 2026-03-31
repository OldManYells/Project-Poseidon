package net.minecraft.server;

import org.bukkit.craftbukkit.item.ItemStack;

public class Slot {

    public final int index; // CraftBukkit - private -> public
    public final IInventory inventory; // CraftBukkit - private -> public
    public int a; // slotNumber
    public int b; // xDisplayPosition
    public int c; // yDisplayPosition

    public Slot(IInventory inventory, int index, int xDisplayPosition, int yDisplayPosition) {
        this.inventory = inventory;
        this.index = index;
        this.b = xDisplayPosition;
        this.c = yDisplayPosition;
    }

    public void onPickupFromSlot(ItemStack itemStack) {
        this.onSlotChanged();
    }

    public boolean isAllowed(ItemStack itemStack) {
        return true;
    }

    public ItemStack getItem() {
        return this.inventory.getItem(this.index);
    }

    public boolean hasItem() {
        return this.getItem() != null;
    }

    public void setItem(ItemStack itemStack) {
        this.inventory.setItem(this.index, itemStack);
        this.onSlotChanged();
    }

    public void onSlotChanged() {
        this.inventory.update();
    }

    public int getMaxStackSize() {
        return this.inventory.getMaxStackSize();
    }

    public ItemStack splitStack(int amount) {
        return this.inventory.splitStack(this.index, amount);
    }

    public boolean isAt(IInventory inventory, int index) {
        return inventory == this.inventory && index == this.index;
    }

    @Deprecated
    public void a(ItemStack itemStack) {
        this.onPickupFromSlot(itemStack);
    }

    @Deprecated
    public boolean b() {
        return this.hasItem();
    }

    @Deprecated
    public void c(ItemStack itemStack) {
        this.setItem(itemStack);
    }

    @Deprecated
    public void c() {
        this.onSlotChanged();
    }

    @Deprecated
    public int d() {
        return this.getMaxStackSize();
    }

    @Deprecated
    public ItemStack a(int amount) {
        return this.splitStack(amount);
    }

    @Deprecated
    public boolean a(IInventory inventory, int index) {
        return this.isAt(inventory, index);
    }
}
