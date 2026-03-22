package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical fade/spread decision policy for legacy grass wrappers.
 */
public final class GrassSpreadBehaviour {
    private static final GrassSpreadBehaviour INSTANCE = new GrassSpreadBehaviour();

    private GrassSpreadBehaviour() {
    }

    public static GrassSpreadBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldAttemptFade(int lightAbove, int aboveOpacity) {
        return lightAbove < 4 && aboveOpacity > 2;
    }

    public boolean shouldPassFadeRandomGate(Random random) {
        return random.nextInt(4) == 0;
    }

    public boolean shouldAttemptSpread(int lightAbove) {
        return lightAbove >= 9;
    }

    public int resolveSpreadTargetX(int baseX, Random random) {
        return baseX + random.nextInt(3) - 1;
    }

    public int resolveSpreadTargetY(int baseY, Random random) {
        return baseY + random.nextInt(5) - 3;
    }

    public int resolveSpreadTargetZ(int baseZ, Random random) {
        return baseZ + random.nextInt(3) - 1;
    }

    public boolean canSpreadTo(int targetTypeId, int dirtBlockId, int targetLightAbove, int minTargetLight, int aboveOpacity, int maxAboveOpacity) {
        return targetTypeId == dirtBlockId && targetLightAbove >= minTargetLight && aboveOpacity <= maxAboveOpacity;
    }
}
