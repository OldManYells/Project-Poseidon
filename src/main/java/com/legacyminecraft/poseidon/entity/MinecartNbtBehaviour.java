package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.EntityMinecart;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

public final class MinecartNbtBehaviour {
    private static final MinecartNbtBehaviour INSTANCE = new MinecartNbtBehaviour();

    private MinecartNbtBehaviour() {
    }

    public static MinecartNbtBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeNbt(EntityMinecart minecart, NBTTagCompound tag) {
        tag.a("Type", minecart.type);
        if (minecart.type == 2) {
            tag.a("PushX", minecart.f);
            tag.a("PushZ", minecart.g);
            tag.a("Fuel", (short) minecart.e);
            return;
        }

        if (minecart.type == 1) {
            NBTTagList itemsTag = new NBTTagList();
            for (int slot = 0; slot < minecart.getSize(); ++slot) {
                ItemStack stack = minecart.getItem(slot);
                if (stack == null) {
                    continue;
                }

                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.a("Slot", (byte) slot);
                stack.a(itemTag);
                itemsTag.a((NBTBase) itemTag);
            }
            tag.a("Items", (NBTBase) itemsTag);
        }
    }

    public void readNbt(EntityMinecart minecart, NBTTagCompound tag) {
        minecart.type = tag.e("Type");
        if (minecart.type == 2) {
            minecart.f = tag.h("PushX");
            minecart.g = tag.h("PushZ");
            minecart.e = tag.d("Fuel");
            return;
        }

        if (minecart.type == 1) {
            for (int slot = 0; slot < minecart.getSize(); ++slot) {
                minecart.setItem(slot, null);
            }

            NBTTagList itemsTag = tag.l("Items");
            for (int index = 0; index < itemsTag.c(); ++index) {
                NBTTagCompound itemTag = (NBTTagCompound) itemsTag.a(index);
                int slot = itemTag.c("Slot") & 255;
                if (slot >= 0 && slot < minecart.getSize()) {
                    minecart.setItem(slot, new ItemStack(itemTag));
                }
            }
        }
    }
}
