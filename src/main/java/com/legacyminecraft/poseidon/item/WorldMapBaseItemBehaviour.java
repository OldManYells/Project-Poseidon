package com.legacyminecraft.poseidon.item;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet;
import net.minecraft.server.World;

public final class WorldMapBaseItemBehaviour {
    private static final WorldMapBaseItemBehaviour INSTANCE = new WorldMapBaseItemBehaviour();

    private WorldMapBaseItemBehaviour() {
    }

    public static WorldMapBaseItemBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isMapRendererItem() {
        return true;
    }

    public Packet createUpdatePacket(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return null;
    }
}
