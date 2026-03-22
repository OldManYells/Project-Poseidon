package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

/**
 * Canonical NBT serialization and deserialization for player inventory arrays.
 */
public final class PlayerInventoryNbtCodecBehaviour {
    private static final PlayerInventoryNbtCodecBehaviour INSTANCE = new PlayerInventoryNbtCodecBehaviour();
    private static final int ARMOR_SLOT_OFFSET = 100;

    private PlayerInventoryNbtCodecBehaviour() {
    }

    public static PlayerInventoryNbtCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public NBTTagList writeInventory(NBTTagList nbtTagList, ItemStack[] items, ItemStack[] armor) {
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound entry = new NBTTagCompound();
                entry.a("Slot", (byte) i);
                items[i].a(entry);
                nbtTagList.a((NBTBase) entry);
            }
        }

        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null) {
                NBTTagCompound entry = new NBTTagCompound();
                entry.a("Slot", (byte) (i + ARMOR_SLOT_OFFSET));
                armor[i].a(entry);
                nbtTagList.a((NBTBase) entry);
            }
        }
        return nbtTagList;
    }

    public InventoryState readInventory(NBTTagList nbtTagList, int itemSlots, int armorSlots) {
        ItemStack[] items = new ItemStack[itemSlots];
        ItemStack[] armor = new ItemStack[armorSlots];

        for (int i = 0; i < nbtTagList.c(); ++i) {
            NBTTagCompound entry = (NBTTagCompound) nbtTagList.a(i);
            int slot = entry.c("Slot") & 255;
            ItemStack itemStack = new ItemStack(entry);

            if (itemStack.getItem() != null) {
                if (slot >= 0 && slot < items.length) {
                    items[slot] = itemStack;
                }
                if (slot >= ARMOR_SLOT_OFFSET && slot < armor.length + ARMOR_SLOT_OFFSET) {
                    armor[slot - ARMOR_SLOT_OFFSET] = itemStack;
                }
            }
        }
        return new InventoryState(items, armor);
    }

    public static final class InventoryState {
        private final ItemStack[] items;
        private final ItemStack[] armor;

        public InventoryState(ItemStack[] items, ItemStack[] armor) {
            this.items = items;
            this.armor = armor;
        }

        public ItemStack[] getItems() {
            return items;
        }

        public ItemStack[] getArmor() {
            return armor;
        }
    }
}
