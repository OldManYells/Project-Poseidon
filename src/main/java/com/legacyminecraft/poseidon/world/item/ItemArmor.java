package com.legacyminecraft.poseidon.world.item;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

public class ItemArmor extends Item {

    private static final int[] bn = new int[] { 3, 8, 6, 3};
    private static final int[] bo = new int[] { 11, 16, 15, 13};
    public final int a;
    public final int bk;
    public final int bl;
    public final int bm;

    public ItemArmor(int i, int j, int k, int l) {
        super(i);
        this.a = j;
        this.bk = l;
        this.bm = k;
        this.bl = bn[l];
        this.d(bo[l] * 3 << j);
        this.maxStackSize = 1;
    }
}
