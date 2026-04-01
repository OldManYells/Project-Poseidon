package com.legacyminecraft.poseidon.world.item;

import com.legacyminecraft.poseidon.packets.Packet;
import com.legacyminecraft.poseidon.world.core.World;
import com.legacyminecraft.poseidon.world.entity.EntityHuman;

public class ItemWorldMapBase extends Item {

    public ItemWorldMapBase(int i) {
        super(i);
    }

    public boolean b() {
        return true;
    }

    public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return null;
    }
}
