package com.legacyminecraft.poseidon.world.item;

public class ItemSapling extends ItemBlock {

    public ItemSapling(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }
}
