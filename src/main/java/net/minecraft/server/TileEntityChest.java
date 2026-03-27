package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ChestTileInventoryBehaviour;

public class TileEntityChest extends TileEntity implements IInventory {
    private static final ChestTileInventoryBehaviour CHEST_TILE_INVENTORY_BEHAVIOUR = ChestTileInventoryBehaviour.getInstance();

    private ItemStack[] items = new ItemStack[CHEST_TILE_INVENTORY_BEHAVIOUR.getSize()]; // CraftBukkit

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

    public TileEntityChest() {}

    public int getSize() {
        return CHEST_TILE_INVENTORY_BEHAVIOUR.getSize();
    }

    public ItemStack getItem(int i) {
        return (ItemStack) CHEST_TILE_INVENTORY_BEHAVIOUR.getItem(this.items, i);
    }

    public ItemStack splitStack(int i, int j) {
        ItemStack split = (ItemStack) CHEST_TILE_INVENTORY_BEHAVIOUR.splitStack(this.items, i, j);
        if (split != null) {
            this.update();
        }
        return split;
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items[i] = (ItemStack) CHEST_TILE_INVENTORY_BEHAVIOUR.clampStackSize(itemstack);
        this.update();
    }

    public String getName() {
        return CHEST_TILE_INVENTORY_BEHAVIOUR.getName();
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.items = (ItemStack[]) CHEST_TILE_INVENTORY_BEHAVIOUR.readItems(nbttagcompound, new ItemStack[this.getSize()]);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        CHEST_TILE_INVENTORY_BEHAVIOUR.writeItems(nbttagcompound, this.items);
    }

    public int getMaxStackSize() {
        return CHEST_TILE_INVENTORY_BEHAVIOUR.getMaxStackSize();
    }

    public boolean a_(EntityHuman entityhuman) {
        return CHEST_TILE_INVENTORY_BEHAVIOUR.canUse(this.world, this.x, this.y, this.z, this, entityhuman);
    }
}
