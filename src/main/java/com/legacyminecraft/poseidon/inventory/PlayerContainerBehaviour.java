package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.InventoryCraftResult;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Slot;
import net.minecraft.server.SlotResult;

import java.util.List;

public final class PlayerContainerBehaviour {
    private static final PlayerContainerBehaviour INSTANCE = new PlayerContainerBehaviour();

    private PlayerContainerBehaviour() {
    }

    public static PlayerContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public interface ArmorSlotFactory {
        Slot create(int index);
    }

    public InventoryCrafting createCraftInventory(Container container) {
        return new InventoryCrafting(container, 2, 2);
    }

    public IInventory createResultInventory() {
        return new InventoryCraftResult();
    }

    public void initializeSlots(Container container, InventoryPlayer inventoryplayer, InventoryCrafting craftInventory, IInventory resultInventory, ArmorSlotFactory armorSlotFactory) {
        container.poseidonAddSlot(new SlotResult(inventoryplayer.d, craftInventory, resultInventory, 0, 144, 36));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                container.poseidonAddSlot(new Slot(craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }

        for (int i = 0; i < 4; ++i) {
            container.poseidonAddSlot(armorSlotFactory.create(i));
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                container.poseidonAddSlot(new Slot(inventoryplayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            container.poseidonAddSlot(new Slot(inventoryplayer, i, 8 + i * 18, 142));
        }
    }

    public void updateCraftResult(InventoryCrafting craftInventory, IInventory resultInventory, List listeners) {
        ItemStack craftResult = net.minecraft.server.CraftingManager.getInstance().craft(craftInventory);
        resultInventory.setItem(0, craftResult);
        if (listeners.size() < 1) {
            return;
        }

        EntityPlayer player = (EntityPlayer) listeners.get(0);
        player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
    }

    public void onClose(Container container, EntityHuman entityhuman, InventoryCrafting craftInventory) {
        for (int i = 0; i < 4; ++i) {
            ItemStack itemstack = craftInventory.getItem(i);

            if (itemstack != null) {
                entityhuman.b(itemstack);
                craftInventory.setItem(i, (ItemStack) null);
            }
        }
    }

    public boolean canUse(EntityHuman entityhuman) {
        return true;
    }

    public ItemStack quickMove(Container container, List slots, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) slots.get(index);

        if (slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (index == 0) {
                container.poseidonMergeItemStack(itemstack1, 9, 45, true);
            } else if (index >= 9 && index < 36) {
                container.poseidonMergeItemStack(itemstack1, 36, 45, false);
            } else if (index >= 36 && index < 45) {
                container.poseidonMergeItemStack(itemstack1, 9, 36, false);
            } else {
                container.poseidonMergeItemStack(itemstack1, 9, 45, false);
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
}
