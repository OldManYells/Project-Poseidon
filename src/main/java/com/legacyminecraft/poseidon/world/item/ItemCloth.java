package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.*;

public class ItemCloth extends ItemBlock {

    public ItemCloth(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }
}
