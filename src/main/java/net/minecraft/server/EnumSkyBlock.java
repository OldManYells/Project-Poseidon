package net.minecraft.server;

import com.legacyminecraft.poseidon.world.types.SkyLightTypeContract;

public enum EnumSkyBlock implements SkyLightTypeContract {

    SKY("Sky", 0, 15), BLOCK("Block", 1, 0);
    public final int c;

    private static final EnumSkyBlock[] d = new EnumSkyBlock[] { SKY, BLOCK};

    private EnumSkyBlock(String s, int i, int j) {
        this.c = j;
    }

    public int getDefaultLightValue() {
        return this.c;
    }
}
