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

public class ItemFood extends Item {

    private int a;
    private boolean bk;

    public ItemFood(int i, int j, boolean flag) {
        super(i);
        this.a = j;
        this.bk = flag;
        this.maxStackSize = 1;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        --itemstack.count;
        entityhuman.b(this.a);
        return itemstack;
    }

    public int k() {
        return this.a;
    }

    public boolean l() {
        return this.bk;
    }
}
