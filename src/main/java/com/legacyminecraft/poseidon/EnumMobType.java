package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public enum EnumMobType {

    EVERYTHING("everything", 0), MOBS("mobs", 1), PLAYERS("players", 2);

    private static final EnumMobType[] d = new EnumMobType[] { EVERYTHING, MOBS, PLAYERS};

    private EnumMobType(String s, int i) {}
}
