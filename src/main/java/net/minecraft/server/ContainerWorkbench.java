package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.entity.EntityPlayer;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.network.Packet103SetSlot;
import org.bukkit.craftbukkit.world.World;

public class ContainerWorkbench extends Container {

    public InventoryCrafting craftInventory = new InventoryCrafting(this, 3, 3);
    public IInventory resultInventory = new InventoryCraftResult();
    private World world;
    private int x;
    private int y;
    private int z;

    public ContainerWorkbench(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.addSlot(new SlotResult(inventoryPlayer.d, this.craftInventory, this.resultInventory, 0, 124, 35));

        int row;
        int column;

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 3; ++column) {
                this.addSlot(new Slot(this.craftInventory, column + row * 3, 30 + column * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventoryPlayer, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
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
        if (!this.world.isStatic) {
            for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
                ItemStack itemStack = this.craftInventory.getItem(slotIndex);
                if (itemStack != null) {
                    player.b(itemStack);
                }
            }
        }
    }

    public boolean canUse(EntityHuman player) {
        return this.world.getTypeId(this.x, this.y, this.z) != CraftBlock.WORKBENCH.id ? false : player.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    public ItemStack transferStackInSlot(int slotIndex) {
        ItemStack result = null;
        Slot slot = (Slot) this.e.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.cloneItemStack();

            if (slotIndex == 0) {
                this.mergeItemStack(slotStack, 10, 46, true);
            } else if (slotIndex >= 10 && slotIndex < 37) {
                this.mergeItemStack(slotStack, 37, 46, false);
            } else if (slotIndex >= 37 && slotIndex < 46) {
                this.mergeItemStack(slotStack, 10, 37, false);
            } else {
                this.mergeItemStack(slotStack, 10, 46, false);
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
