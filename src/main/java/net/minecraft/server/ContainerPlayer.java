package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.entity.EntityPlayer;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.network.Packet103SetSlot;

public class ContainerPlayer extends Container {

    public InventoryCrafting craftInventory;
    public IInventory resultInventory;
    public boolean c;

    public ContainerPlayer(InventoryPlayer inventoryPlayer) {
        this(inventoryPlayer, true);
    }

    public ContainerPlayer(InventoryPlayer inventoryPlayer, boolean isLocalWorld) {
        this.craftInventory = new InventoryCrafting(this, 2, 2);
        this.resultInventory = new InventoryCraftResult();
        this.c = false;
        this.c = isLocalWorld;
        this.addSlot(new SlotResult(inventoryPlayer.d, this.craftInventory, this.resultInventory, 0, 144, 36));

        int row;
        int column;

        for (row = 0; row < 2; ++row) {
            for (column = 0; column < 2; ++column) {
                this.addSlot(new Slot(this.craftInventory, column + row * 2, 88 + column * 18, 26 + row * 18));
            }
        }

        for (row = 0; row < 4; ++row) {
            this.addSlot(new SlotArmor(this, inventoryPlayer, inventoryPlayer.getSize() - 1 - row, 8, 8 + row * 18, row));
        }

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventoryPlayer, column + (row + 1) * 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(inventoryPlayer, row, 8 + row * 18, 142));
        }

        this.onInventoryChanged(this.craftInventory);
    }

    public void onInventoryChanged(IInventory inventory) {
        ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory);
        this.resultInventory.setItem(0, craftResult);
        if (super.listeners.size() < 1) {
            return;
        }

        EntityPlayer player = (EntityPlayer) super.listeners.get(0);
        player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
    }

    public void onContainerClosed(EntityHuman player) {
        super.onContainerClosed(player);

        for (int slotIndex = 0; slotIndex < 4; ++slotIndex) {
            ItemStack itemStack = this.craftInventory.getItem(slotIndex);

            if (itemStack != null) {
                player.b(itemStack);
                this.craftInventory.setItem(slotIndex, (ItemStack) null);
            }
        }
    }

    public boolean canUse(EntityHuman player) {
        return true;
    }

    public ItemStack transferStackInSlot(int slotIndex) {
        ItemStack result = null;
        Slot slot = (Slot) this.e.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.cloneItemStack();

            if (slotIndex == 0) {
                this.mergeItemStack(slotStack, 9, 45, true);
            } else if (slotIndex >= 9 && slotIndex < 36) {
                this.mergeItemStack(slotStack, 36, 45, false);
            } else if (slotIndex >= 36 && slotIndex < 45) {
                this.mergeItemStack(slotStack, 9, 36, false);
            } else {
                this.mergeItemStack(slotStack, 9, 45, false);
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
    public void a(IInventory inventory) {
        this.onInventoryChanged(inventory);
    }

    @Deprecated
    public void a(EntityHuman player) {
        this.onContainerClosed(player);
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
