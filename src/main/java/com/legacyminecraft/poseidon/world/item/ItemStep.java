package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.*;

public class ItemStep extends ItemBlock {

    public ItemStep(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }
}
