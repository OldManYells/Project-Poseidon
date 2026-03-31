package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.item.ItemStack;

public class ContainerFurnace extends Container {

    private TileEntityFurnace furnace;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastCurrentItemBurnTime = 0;

    public ContainerFurnace(InventoryPlayer inventoryPlayer, TileEntityFurnace furnace) {
        this.furnace = furnace;
        this.addSlot(new Slot(furnace, 0, 56, 17));
        this.addSlot(new Slot(furnace, 1, 56, 53));
        this.addSlot(new SlotResult2(inventoryPlayer.d, furnace, 2, 116, 35));

        int row;
        for (row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventoryPlayer, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(inventoryPlayer, row, 8 + row * 18, 142));
        }
    }

    public void addSlotListener(ICrafting listener) {
        super.addSlotListener(listener);
        listener.a(this, 0, this.furnace.cookTime);
        listener.a(this, 1, this.furnace.burnTime);
        listener.a(this, 2, this.furnace.ticksForCurrentFuel);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int listenerIndex = 0; listenerIndex < this.listeners.size(); ++listenerIndex) {
            ICrafting listener = (ICrafting) this.listeners.get(listenerIndex);

            if (this.lastCookTime != this.furnace.cookTime) {
                listener.a(this, 0, this.furnace.cookTime);
            }

            if (this.lastBurnTime != this.furnace.burnTime) {
                listener.a(this, 1, this.furnace.burnTime);
            }

            if (this.lastCurrentItemBurnTime != this.furnace.ticksForCurrentFuel) {
                listener.a(this, 2, this.furnace.ticksForCurrentFuel);
            }
        }

        this.lastCookTime = this.furnace.cookTime;
        this.lastBurnTime = this.furnace.burnTime;
        this.lastCurrentItemBurnTime = this.furnace.ticksForCurrentFuel;
    }

    public boolean canUse(EntityHuman player) {
        return this.furnace.a_(player);
    }

    public ItemStack transferStackInSlot(int slotIndex) {
        ItemStack result = null;
        Slot slot = (Slot) this.e.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.cloneItemStack();

            if (slotIndex == 2) {
                this.mergeItemStack(slotStack, 3, 39, true);
            } else if (slotIndex >= 3 && slotIndex < 30) {
                this.mergeItemStack(slotStack, 30, 39, false);
            } else if (slotIndex >= 30 && slotIndex < 39) {
                this.mergeItemStack(slotStack, 3, 30, false);
            } else {
                this.mergeItemStack(slotStack, 3, 39, false);
            }

            if (slotStack.count == 0) {
                slot.setItem((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.count == result.count) {
                return null;
            }

            slot.onPickupFromSlot(slotStack);
        }

        return result;
    }

    @Deprecated
    public void a(ICrafting listener) {
        this.addSlotListener(listener);
    }

    @Deprecated
    public void a() {
        this.detectAndSendChanges();
    }

    @Deprecated
    public boolean b(EntityHuman player) {
        return this.canUse(player);
    }

    @Deprecated
    public ItemStack a(int slotIndex) {
        return this.transferStackInSlot(slotIndex);
    }
}
