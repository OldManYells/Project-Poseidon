package net.minecraft.server;

import com.legacyminecraft.poseidon.world.types.MovingObjectTypeContract;

public enum EnumMovingObjectType implements MovingObjectTypeContract {

    TILE("TILE", 0), ENTITY("ENTITY", 1);

    private static final EnumMovingObjectType[] c = new EnumMovingObjectType[] { TILE, ENTITY};

    private EnumMovingObjectType(String s, int i) {}
}
