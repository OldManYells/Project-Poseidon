package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.*;

public class ItemLeaves extends ItemBlock {

    public ItemLeaves(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i | 8;
    }
}
