package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public enum EnumMovingObjectType {

    TILE("TILE", 0), ENTITY("ENTITY", 1);

    private static final EnumMovingObjectType[] c = new EnumMovingObjectType[] { TILE, ENTITY};

    private EnumMovingObjectType(String s, int i) {}
}
