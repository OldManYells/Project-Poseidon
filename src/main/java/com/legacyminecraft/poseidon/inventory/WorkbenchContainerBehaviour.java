package com.legacyminecraft.poseidon.inventory;


import java.util.List;

public final class WorkbenchContainerBehaviour {
    private static final WorkbenchContainerBehaviour INSTANCE = new WorkbenchContainerBehaviour();

    private WorkbenchContainerBehaviour() {
    }

    public static WorkbenchContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSlots(Container container, InventoryPlayer inventoryplayer, InventoryCrafting craftInventory, IInventory resultInventory) {
        container.poseidonAddSlot(new SlotResult(inventoryplayer.d, craftInventory, resultInventory, 0, 124, 35));

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                container.poseidonAddSlot(new Slot(craftInventory, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                container.poseidonAddSlot(new Slot(inventoryplayer, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            container.poseidonAddSlot(new Slot(inventoryplayer, col, 8 + col * 18, 142));
        }
    }

    public void updateCraftResult(Container container, InventoryCrafting craftInventory, IInventory resultInventory, List listeners) {
        ItemStack craftResult = CraftingManager.getInstance().craft(craftInventory);
        resultInventory.setItem(0, craftResult);
        if (listeners.size() < 1) {
            return;
        }

        EntityPlayer player = (EntityPlayer) listeners.get(0);
        player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
    }

    public void onClose(Container container, EntityHuman entityhuman, World world, InventoryCrafting craftInventory) {
        if (!world.isStatic) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemstack = craftInventory.getItem(i);

                if (itemstack != null) {
                    entityhuman.b(itemstack);
                }
            }
        }
    }

    public boolean canUse(World world, int x, int y, int z, EntityHuman entityhuman) {
        return world.getTypeId(x, y, z) == Block.WORKBENCH.id
                && entityhuman.e((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D) <= 64.0D;
    }

    public ItemStack quickMove(Container container, List slots, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) slots.get(index);

        if (slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (index == 0) {
                container.poseidonMergeItemStack(itemstack1, 10, 46, true);
            } else if (index >= 10 && index < 37) {
                container.poseidonMergeItemStack(itemstack1, 37, 46, false);
            } else if (index >= 37 && index < 46) {
                container.poseidonMergeItemStack(itemstack1, 10, 37, false);
            } else {
                container.poseidonMergeItemStack(itemstack1, 10, 46, false);
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

    public InventoryCrafting createCraftInventory(Container container) {
        return new InventoryCrafting(container, 3, 3);
    }

    public IInventory createResultInventory() {
        return new InventoryCraftResult();
    }
}
