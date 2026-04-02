package com.legacyminecraft.poseidon.world.item;

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
