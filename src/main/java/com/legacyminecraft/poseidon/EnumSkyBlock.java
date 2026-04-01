package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public enum EnumSkyBlock {

    SKY("Sky", 0, 15), BLOCK("Block", 1, 0);
    public final int c;

    private static final EnumSkyBlock[] d = new EnumSkyBlock[] { SKY, BLOCK};

    private EnumSkyBlock(String s, int i, int j) {
        this.c = j;
    }
}
