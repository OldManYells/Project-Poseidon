package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.LargeChestInventoryBehaviour;

public class InventoryLargeChest implements IInventory {

    private String a;
    private IInventory b;
    private IInventory c;
    private final LargeChestInventoryBehaviour largeChestInventoryService = LargeChestInventoryBehaviour.getInstance();

    // CraftBukkit start
    public ItemStack[] getContents() {
        return largeChestInventoryService.getContents(this.b, this.c);
    }
    // CraftBukkit end

    public InventoryLargeChest(String s, IInventory iinventory, IInventory iinventory1) {
        this.a = s;
        this.b = iinventory;
        this.c = iinventory1;
    }

    public int getSize() {
        return largeChestInventoryService.getSize(this.b, this.c);
    }

    public String getName() {
        return this.a;
    }

    public ItemStack getItem(int i) {
        return largeChestInventoryService.getItem(this.b, this.c, i);
    }

    public ItemStack splitStack(int i, int j) {
        return largeChestInventoryService.splitStack(this.b, this.c, i, j);
    }

    public void setItem(int i, ItemStack itemstack) {
        largeChestInventoryService.setItem(this.b, this.c, i, itemstack);
    }

    public int getMaxStackSize() {
        return this.b.getMaxStackSize();
    }

    public void update() {
        largeChestInventoryService.update(this.b, this.c);
    }

    public boolean a_(EntityHuman entityhuman) {
        return largeChestInventoryService.canUse(this.b, this.c, entityhuman);
    }
}
