package com.legacyminecraft.poseidon.inventory;


import java.util.List;

public final class FurnaceContainerBehaviour {
    private static final FurnaceContainerBehaviour INSTANCE = new FurnaceContainerBehaviour();

    private FurnaceContainerBehaviour() {
    }

    public static FurnaceContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSlots(Container container, InventoryPlayer inventoryplayer, TileEntityFurnace tileentityfurnace) {
        container.poseidonAddSlot(new Slot(tileentityfurnace, 0, 56, 17));
        container.poseidonAddSlot(new Slot(tileentityfurnace, 1, 56, 53));
        container.poseidonAddSlot(new SlotResult2(inventoryplayer.d, tileentityfurnace, 2, 116, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                container.poseidonAddSlot(new Slot(inventoryplayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            container.poseidonAddSlot(new Slot(inventoryplayer, i, 8 + i * 18, 142));
        }
    }

    public void sendInitialProgress(Container container, ICrafting icrafting, TileEntityFurnace tileentityfurnace) {
        icrafting.a(container, 0, tileentityfurnace.cookTime);
        icrafting.a(container, 1, tileentityfurnace.burnTime);
        icrafting.a(container, 2, tileentityfurnace.ticksForCurrentFuel);
    }

    public ProgressState broadcastProgress(Container container, List listeners, TileEntityFurnace tileentityfurnace,
                                           int lastCookTime, int lastBurnTime, int lastFuelTicks) {
        for (int i = 0; i < listeners.size(); ++i) {
            ICrafting icrafting = (ICrafting) listeners.get(i);

            if (lastCookTime != tileentityfurnace.cookTime) {
                icrafting.a(container, 0, tileentityfurnace.cookTime);
            }

            if (lastBurnTime != tileentityfurnace.burnTime) {
                icrafting.a(container, 1, tileentityfurnace.burnTime);
            }

            if (lastFuelTicks != tileentityfurnace.ticksForCurrentFuel) {
                icrafting.a(container, 2, tileentityfurnace.ticksForCurrentFuel);
            }
        }

        return new ProgressState(tileentityfurnace.cookTime, tileentityfurnace.burnTime, tileentityfurnace.ticksForCurrentFuel);
    }

    public boolean canUse(TileEntityFurnace tileentityfurnace, EntityHuman entityhuman) {
        return tileentityfurnace.a_(entityhuman);
    }

    public ItemStack quickMove(Container container, List slots, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) slots.get(index);

        if (slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (index == 2) {
                container.poseidonMergeItemStack(itemstack1, 3, 39, true);
            } else if (index >= 3 && index < 30) {
                container.poseidonMergeItemStack(itemstack1, 30, 39, false);
            } else if (index >= 30 && index < 39) {
                container.poseidonMergeItemStack(itemstack1, 3, 30, false);
            } else {
                container.poseidonMergeItemStack(itemstack1, 3, 39, false);
            }

            if (itemstack1.count == 0) {
                slot.c((ItemStack) null);
            } else {
                slot.c();
            }

            if (itemstack1.count == itemstack.count) {
                return null;
            }

            slot.a(itemstack1);
        }

        return itemstack;
    }

    public static final class ProgressState {
        public final int cookTime;
        public final int burnTime;
        public final int fuelTicks;

        ProgressState(int cookTime, int burnTime, int fuelTicks) {
            this.cookTime = cookTime;
            this.burnTime = burnTime;
            this.fuelTicks = fuelTicks;
        }
    }
}
