package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

public class ItemCookie extends ItemFood {

    public ItemCookie(int i, int j, boolean flag, int k) {
        super(i, j, flag);
        this.maxStackSize = k;
    }
}
