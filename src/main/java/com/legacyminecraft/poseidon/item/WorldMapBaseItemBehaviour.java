package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.entity.EntityHuman;
import com.legacyminecraft.poseidon.packet.Packet;
import com.legacyminecraft.poseidon.world.World;

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
