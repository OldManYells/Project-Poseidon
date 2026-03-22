package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftingInventoryBehaviour;

public class InventoryCrafting implements IInventory {

    private ItemStack[] items;
    private int b;
    private Container c;
    private final CraftingInventoryBehaviour craftingInventoryService = CraftingInventoryBehaviour.getInstance();

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

    public InventoryCrafting(Container container, int i, int j) {
        this.items = craftingInventoryService.createGridStorage(i, j);
        this.c = container;
        this.b = i;
    }

    public int getSize() {
        return this.items.length;
    }

    public ItemStack getItem(int i) {
        return craftingInventoryService.getByIndex(this.items, this.getSize(), i);
    }

    public ItemStack b(int i, int j) {
        return craftingInventoryService.getByGridPosition(this.items, this.b, i, j);
    }

    public String getName() {
        return "Crafting";
    }

    public ItemStack splitStack(int i, int j) {
        CraftingInventoryBehaviour.SplitResult splitResult = craftingInventoryService.split(this.items, i, j);
        if (splitResult.isChanged()) {
            this.c.a((IInventory) this);
        }
        return splitResult.getItemStack();
    }

    public void setItem(int i, ItemStack itemstack) {
        craftingInventoryService.set(this.items, i, itemstack);
        this.c.a((IInventory) this);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {}

    public boolean a_(EntityHuman entityhuman) {
        return true;
    }
}
