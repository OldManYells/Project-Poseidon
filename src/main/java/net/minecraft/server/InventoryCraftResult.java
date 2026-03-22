package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftResultInventoryBehaviour;

public class InventoryCraftResult implements IInventory {

    private ItemStack[] items = CraftResultInventoryBehaviour.getInstance().createStorage();
    private final CraftResultInventoryBehaviour craftResultInventoryService = CraftResultInventoryBehaviour.getInstance();

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

    public InventoryCraftResult() {}

    public int getSize() {
        return 1;
    }

    public ItemStack getItem(int i) {
        return craftResultInventoryService.get(this.items, i);
    }

    public String getName() {
        return "Result";
    }

    public ItemStack splitStack(int i, int j) {
        return craftResultInventoryService.split(this.items, i);
    }

    public void setItem(int i, ItemStack itemstack) {
        craftResultInventoryService.set(this.items, i, itemstack);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {}

    public boolean a_(EntityHuman entityhuman) {
        return true;
    }
}
