package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;
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
