package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.packets.*;
import com.legacyminecraft.poseidon.*;

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
