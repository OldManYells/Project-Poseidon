package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

public class ItemSoup extends ItemFood {

    public ItemSoup(int i, int j) {
        super(i, j, false);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        super.a(itemstack, world, entityhuman);
        return new ItemStack(Item.BOWL);
    }
}
