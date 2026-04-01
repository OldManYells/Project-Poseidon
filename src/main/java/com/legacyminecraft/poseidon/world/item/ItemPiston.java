package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

public class ItemPiston extends ItemBlock {

    public ItemPiston(int i) {
        super(i);
    }

    public int filterData(int i) {
        return 7;
    }
}
