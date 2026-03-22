package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

/**
 * Canonical NBT codec behaviour for furnace inventory and timers.
 */
public final class FurnaceNbtCodecBehaviour {
    private static final FurnaceNbtCodecBehaviour INSTANCE = new FurnaceNbtCodecBehaviour();

    private FurnaceNbtCodecBehaviour() {
    }

    public static FurnaceNbtCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public FurnaceNbtState readState(NBTTagCompound nbt, int inventorySize) {
        NBTTagList itemList = nbt.l("Items");
        ItemStack[] items = new ItemStack[inventorySize];

        for (int index = 0; index < itemList.c(); ++index) {
            NBTTagCompound itemNbt = (NBTTagCompound) itemList.a(index);
            byte slotIndex = itemNbt.c("Slot");

            if (slotIndex >= 0 && slotIndex < items.length) {
                items[slotIndex] = new ItemStack(itemNbt);
            }
        }

        int burnTime = nbt.d("BurnTime");
        int cookTime = nbt.d("CookTime");
        return new FurnaceNbtState(items, burnTime, cookTime);
    }

    public void writeState(NBTTagCompound nbt, ItemStack[] items, int burnTime, int cookTime) {
        nbt.a("BurnTime", (short) burnTime);
        nbt.a("CookTime", (short) cookTime);

        NBTTagList itemList = new NBTTagList();
        for (int slotIndex = 0; slotIndex < items.length; ++slotIndex) {
            if (items[slotIndex] == null) {
                continue;
            }

            NBTTagCompound itemNbt = new NBTTagCompound();
            itemNbt.a("Slot", (byte) slotIndex);
            items[slotIndex].a(itemNbt);
            itemList.a((NBTBase) itemNbt);
        }

        nbt.a("Items", (NBTBase) itemList);
    }

    public static final class FurnaceNbtState {
        private final ItemStack[] items;
        private final int burnTime;
        private final int cookTime;

        public FurnaceNbtState(ItemStack[] items, int burnTime, int cookTime) {
            this.items = items;
            this.burnTime = burnTime;
            this.cookTime = cookTime;
        }

        public ItemStack[] getItems() {
            return items;
        }

        public int getBurnTime() {
            return burnTime;
        }

        public int getCookTime() {
            return cookTime;
        }
    }
}
