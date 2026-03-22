package net.minecraft.server;

import com.legacyminecraft.poseidon.world.types.MobSelectionTypeContract;

public enum EnumMobType implements MobSelectionTypeContract {

    EVERYTHING("everything", 0), MOBS("mobs", 1), PLAYERS("players", 2);

    private static final EnumMobType[] d = new EnumMobType[] { EVERYTHING, MOBS, PLAYERS};

    private EnumMobType(String s, int i) {}
}
