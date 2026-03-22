package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;

import java.util.List;

public final class ChestContainerBehaviour {
    private static final ChestContainerBehaviour INSTANCE = new ChestContainerBehaviour();

    private ChestContainerBehaviour() {
    }

    public static ChestContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public int initializeSlots(Container container, IInventory playerInventory, IInventory chestInventory) {
        int chestRows = chestInventory.getSize() / 9;
        int verticalOffset = (chestRows - 4) * 18;

        int row;
        int column;

        for (row = 0; row < chestRows; ++row) {
            for (column = 0; column < 9; ++column) {
                container.poseidonAddSlot(new Slot(chestInventory, column + row * 9, 8 + column * 18, 18 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                container.poseidonAddSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 103 + row * 18 + verticalOffset));
            }
        }

        for (row = 0; row < 9; ++row) {
            container.poseidonAddSlot(new Slot(playerInventory, row, 8 + row * 18, 161 + verticalOffset));
        }

        return chestRows;
    }

    public boolean canUse(IInventory chestInventory, EntityHuman human) {
        return chestInventory.a_(human);
    }

    public ItemStack quickMove(Container container, List slots, int index, int chestRows) {
        ItemStack movedStack = null;
        Slot slot = (Slot) slots.get(index);

        if (slot != null && slot.b()) {
            ItemStack slotStack = slot.getItem();

            movedStack = slotStack.cloneItemStack();
            if (index < chestRows * 9) {
                container.poseidonMergeItemStack(slotStack, chestRows * 9, slots.size(), true);
            } else {
                container.poseidonMergeItemStack(slotStack, 0, chestRows * 9, false);
            }

            if (slotStack.count == 0) {
                slot.c((ItemStack) null);
            } else {
                slot.c();
            }
        }

        return movedStack;
    }
}
