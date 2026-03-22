package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;

public final class ChestTileInventoryBehaviour {
    private static final ChestTileInventoryBehaviour INSTANCE = new ChestTileInventoryBehaviour();
    private static final int DEFAULT_SIZE = 27;
    private static final int MAX_STACK = 64;

    private ChestTileInventoryBehaviour() {
    }

    public static ChestTileInventoryBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack[] createStorage() {
        return new ItemStack[DEFAULT_SIZE];
    }

    public int getSize() {
        return DEFAULT_SIZE;
    }

    public ItemStack getItem(ItemStack[] items, int index) {
        return items[index];
    }

    public ItemStack splitStack(ItemStack[] items, int index, int amount) {
        if (items[index] != null) {
            ItemStack split;

            if (items[index].count <= amount) {
                split = items[index];
                items[index] = null;
                return split;
            }

            split = items[index].a(amount);
            if (items[index].count == 0) {
                items[index] = null;
            }

            return split;
        }

        return null;
    }

    public ItemStack clampStackSize(ItemStack itemstack) {
        if (itemstack != null && itemstack.count > MAX_STACK) {
            itemstack.count = MAX_STACK;
        }
        return itemstack;
    }

    public String getName() {
        return "Chest";
    }

    public ItemStack[] readItems(NBTTagCompound tag, int size) {
        NBTTagList list = tag.l("Items");
        ItemStack[] items = new ItemStack[size];

        for (int i = 0; i < list.c(); ++i) {
            NBTTagCompound itemTag = (NBTTagCompound) list.a(i);
            int slot = itemTag.c("Slot") & 255;

            if (slot >= 0 && slot < items.length) {
                items[slot] = new ItemStack(itemTag);
            }
        }

        return items;
    }

    public void writeItems(NBTTagCompound tag, ItemStack[] items) {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.a("Slot", (byte) i);
                items[i].a(itemTag);
                list.a((NBTBase) itemTag);
            }
        }

        tag.a("Items", (NBTBase) list);
    }

    public int getMaxStackSize() {
        return MAX_STACK;
    }

    public boolean canUse(World world, int x, int y, int z, TileEntityChest self, EntityHuman human) {
        return world.getTileEntity(x, y, z) != self ? false : human.e((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D) <= 64.0D;
    }
}
